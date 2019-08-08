package com.wust.springcloud.common.interceptors;

import com.wust.springcloud.common.interceptors.dataprivilege.StrategyContext;
import com.wust.springcloud.common.annotations.PrivilegeAnnotation;
import com.wust.springcloud.common.dto.PageDto;
import com.wust.springcloud.common.util.ReflectHelper;
import com.wust.springcloud.common.util.cache.SpringRedisTools;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import javax.xml.bind.PropertyException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

/**
 * @author ：wust
 * @date ：Created in 2019/8/7 17:17
 * @description：
 * @version:
 */
@Component
public class MyBatisDefaultInterceptor implements Interceptor {
    private static String dialect = "mysql";	//数据库方言
    private static String pageSqlId = "listPage"; //mapper.xml中需要拦截的ID(正则匹配)

    @Autowired
    private SpringRedisTools springRedisTools;


    public Object intercept(Invocation ivk) throws Throwable {
        if(ivk.getTarget() instanceof RoutingStatementHandler){
            RoutingStatementHandler statementHandler = (RoutingStatementHandler)ivk.getTarget();
            BaseStatementHandler delegate = (BaseStatementHandler) ReflectHelper.getValueByFieldName(statementHandler, "delegate");
            MappedStatement mappedStatement = (MappedStatement) ReflectHelper.getValueByFieldName(delegate, "mappedStatement");
            String namespace = mappedStatement.getId();
            String methodName = namespace.substring(namespace.lastIndexOf('.') + 1,namespace.length());


            /**
             * 数据权限处理
             */
            boolean isPrivilegeAnnotation = isPrivilegeAnnotation(mappedStatement);
            if(isPrivilegeAnnotation){
                processPrivilege(delegate);
            }



            /**
             * 分页处理
             */
            if(methodName.equals("listPage") || methodName.endsWith("ListPage")){
                Connection connection = (Connection) ivk.getArgs()[0];
                processPagination(mappedStatement,delegate,connection);
            }
        }
        return ivk.proceed();
    }




    public Object plugin(Object arg0) {
        if (arg0 instanceof StatementHandler) {
            return Plugin.wrap(arg0, this);
        }
        return arg0;
    }

    public void setProperties(Properties p) {
        dialect = p.getProperty("dialect");
        if (StringUtils.isEmpty(dialect)) {
            try {
                throw new PropertyException("dialect property is not found!");
            } catch (PropertyException e) {
            }
        }
        pageSqlId = p.getProperty("pageSqlId");
        if (StringUtils.isEmpty(pageSqlId)) {
            try {
                throw new PropertyException("pageSqlId property is not found!");
            } catch (PropertyException e) {
            }
        }
    }

    /**
     * 数据权限处理
     * @param delegate
     * @throws SQLException
     * @throws NoSuchFieldException
     */
    private void processPrivilege(BaseStatementHandler delegate)  {
        StrategyContext.getInstance().bindSql(delegate);
    }

    /**
     * 分页处理
     * @param mappedStatement
     * @param delegate
     * @param connection
     * @throws SQLException
     * @throws NoSuchFieldException
     */
    private void processPagination(MappedStatement mappedStatement,BaseStatementHandler delegate,Connection connection) throws SQLException, NoSuchFieldException {
        BoundSql boundSql = delegate.getBoundSql();
        Object parameterObject = boundSql.getParameterObject();//分页SQL<select>中parameterType属性对应的实体参数，即Mapper接口中执行分页方法的参数,该参数不得为空
        if(parameterObject == null){
            throw new NullPointerException("parameterObject尚未实例化！");
        }else{

            String sql = boundSql.getSql();
            String countSql = "select count(0) from (" + sql+ ") as tmp_count"; //记录统计
            PreparedStatement countStmt = connection.prepareStatement(countSql);
            BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(),countSql,boundSql.getParameterMappings(),parameterObject);
            setParameters(countStmt,mappedStatement,countBS,parameterObject);
            ResultSet rs = countStmt.executeQuery();
            int count = 0;
            if (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            countStmt.close();
            PageDto page = null;
            if(parameterObject instanceof PageDto){	//参数就是PageDto实体
                page = (PageDto) parameterObject;
                page.setTotalResult(count);
            }else{	//参数为某个实体，该实体拥有Page属性
                Field pageField = ReflectHelper.getFieldByFieldName(parameterObject,"pageDto");
                if(pageField != null){
                    page = (PageDto) ReflectHelper.getValueByFieldName(parameterObject,"pageDto");
                    if(page == null) {
                        page = new PageDto();
                    }
                    page.setTotalResult(count);
                    ReflectHelper.setValueByFieldName(parameterObject,"pageDto", page); //通过反射，对实体对象设置分页对象
                }else{
                    throw new NoSuchFieldException(parameterObject.getClass().getName()+"不存在 pageDto 属性！");
                }
            }
            String pageSql = generatePageSql(sql,page);
            ReflectHelper.setValueByFieldName(boundSql, "sql", pageSql); //将分页sql语句反射回BoundSql.
        }
    }


    /**
     * 根据数据库方言，生成特定的分页sql
     * @param sql
     * @param page
     * @return
     */
    private String generatePageSql(String sql,PageDto page){
        if(page!=null && !StringUtils.isEmpty(dialect)){
            StringBuffer pageSql = new StringBuffer();
            if("mysql".equals(dialect)){
                pageSql.append(sql);
                pageSql.append(" limit " + page.getCurrentResult() + "," + page.getShowCount());
            }else if("oracle".equals(dialect)){
                pageSql.append("select * from (select tmp_tb.*,ROWNUM row_id from (");
                pageSql.append(sql);
                pageSql.append(") as tmp_tb where ROWNUM<=");
                pageSql.append(page.getCurrentResult() + page.getShowCount());
                pageSql.append(") where row_id>");
                pageSql.append(page.getCurrentResult());
            }
            return pageSql.toString();
        }else{
            return sql;
        }
    }


    /**
     * 对SQL参数(?)设值,参考org.apache.ibatis.executor.parameter.DefaultParameterHandler
     * @param ps
     * @param mappedStatement
     * @param boundSql
     * @param parameterObject
     * @throws SQLException
     */
    private void setParameters(PreparedStatement ps,MappedStatement mappedStatement,BoundSql boundSql,Object parameterObject) throws SQLException {
        ErrorContext.instance().activity("setting parameters").object(mappedStatement.getParameterMap().getId());
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        if (parameterMappings != null) {
            Configuration configuration = mappedStatement.getConfiguration();
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            MetaObject metaObject = parameterObject == null ? null: configuration.newMetaObject(parameterObject);
            for (int i = 0; i < parameterMappings.size(); i++) {
                ParameterMapping parameterMapping = parameterMappings.get(i);
                if (parameterMapping.getMode() != ParameterMode.OUT) {
                    Object value;
                    String propertyName = parameterMapping.getProperty();
                    PropertyTokenizer prop = new PropertyTokenizer(propertyName);
                    if (parameterObject == null) {
                        value = null;
                    } else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                        value = parameterObject;
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        value = boundSql.getAdditionalParameter(propertyName);
                    } else if (propertyName.startsWith(ForEachSqlNode.ITEM_PREFIX)&& boundSql.hasAdditionalParameter(prop.getName())) {
                        value = boundSql.getAdditionalParameter(prop.getName());
                        if (value != null) {
                            value = configuration.newMetaObject(value).getValue(propertyName.substring(prop.getName().length()));
                        }
                    } else {
                        value = metaObject == null ? null : metaObject.getValue(propertyName);
                    }
                    TypeHandler typeHandler = parameterMapping.getTypeHandler();
                    if (typeHandler == null) {
                        throw new ExecutorException("There was no TypeHandler found for parameter "+ propertyName + " of statement "+ mappedStatement.getId());
                    }
                    typeHandler.setParameter(ps, i + 1, value, parameterMapping.getJdbcType());
                }
            }
        }
    }


    /**
     * 是否开启数据权限过滤
     * @param mappedStatement
     * @return
     * @throws ClassNotFoundException
     */
    private boolean isPrivilegeAnnotation(MappedStatement mappedStatement) throws ClassNotFoundException {
        String namespace = mappedStatement.getId();
        String methodName = namespace.substring(namespace.lastIndexOf('.') + 1,namespace.length());
        String className = namespace.substring(0,namespace.lastIndexOf("."));
        Method[] ms = Class.forName(className).getMethods();
        for(Method m : ms){
            if(m.getName().equals(methodName)){
                Annotation annotation = m.getAnnotation(PrivilegeAnnotation.class);
                if(annotation != null){
                    return true;
                }
                break;
            }
        }
        return false;
    }
}
