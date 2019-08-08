package com.wust.springcloud.common.adapter.dataprivilege;

import com.wust.springcloud.common.context.DefaultBusinessContext;
import com.wust.springcloud.common.util.ReflectHelper;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.mapping.BoundSql;

/**
 * @author ：wust
 * @date ：Created in 2019/8/8 10:12
 * @description：操作层用户策略，默认能看到自己公司的数据，后期可以增加其他限制，比如只能看自己、部门、岗位等数据
 * @version:
 */
public class BusinessUserStrategy implements IStrategy {
    @Override
    public void bindSql(BaseStatementHandler delegate) {
        DefaultBusinessContext ctx = DefaultBusinessContext.getContext();

        BoundSql boundSql = delegate.getBoundSql();

        String sql = boundSql.getSql();

        StringBuffer privilegeSqlStringBuffer = new StringBuffer("SELECT privilege_tmp.* FROM (" + sql + ") privilege_tmp");

        privilegeSqlStringBuffer.append(" WHERE company_id = '" + ctx.getCompanyId() + "'");

        ReflectHelper.setValueByFieldName(boundSql, "sql", privilegeSqlStringBuffer.toString());
    }
}
