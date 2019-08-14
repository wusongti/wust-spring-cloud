package com.wust.springcloud.common.interceptors.dataprivilege;

import com.alibaba.fastjson.JSONArray;
import com.wust.springcloud.common.context.DefaultBusinessContext;
import com.wust.springcloud.common.enums.RedisKeyEnum;
import com.wust.springcloud.common.util.ReflectHelper;
import com.wust.springcloud.common.util.SpringContextHolder;
import com.wust.springcloud.common.util.cache.SpringRedisTools;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.mapping.BoundSql;

import java.util.List;

/**
 * @author ：wust
 * @date ：Created in 2019/8/8 10:12
 * @description：操作层用户策略，默认能看到自己公司的数据，后期可以增加其他限制，比如只能看自己、部门、岗位等数据
 * @version:
 */
public class BusinessUserStrategy implements IStrategy {
    private static SpringRedisTools springRedisTools;
    private static SpringRedisTools getRedisSpringBean(){
        if(springRedisTools == null){
            springRedisTools = SpringContextHolder.getBean("springRedisTools");
        }
        return springRedisTools;
    }

    @Override
    public void bindSql(BaseStatementHandler delegate) {
        DefaultBusinessContext ctx = DefaultBusinessContext.getContext();

        BoundSql boundSql = delegate.getBoundSql();

        String sql = boundSql.getSql();

        StringBuffer privilegeSqlStringBuffer = new StringBuffer("SELECT privilege_tmp.* FROM (" + sql + ") privilege_tmp");

        Object obj = getRedisSpringBean().getByKey(String.format(RedisKeyEnum.REDIS_TABLE_KEY_CURRENT_USER_BRANCH_COMPANY_ID.getStringValue(),ctx.getUserId()));
        if(obj != null){
            List<String> list = JSONArray.parseArray(obj.toString(),String.class);
            if(CollectionUtils.isNotEmpty(list)){
                privilegeSqlStringBuffer.append(" WHERE company_id = '" + list.get(0) + "'");
            }
        }


        ReflectHelper.setValueByFieldName(boundSql, "sql", privilegeSqlStringBuffer.toString());
    }
}
