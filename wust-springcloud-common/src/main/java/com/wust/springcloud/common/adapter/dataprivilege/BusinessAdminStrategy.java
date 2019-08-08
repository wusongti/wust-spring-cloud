package com.wust.springcloud.common.adapter.dataprivilege;

import com.wust.springcloud.common.context.DefaultBusinessContext;
import com.wust.springcloud.common.util.ReflectHelper;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.mapping.BoundSql;

/**
 * @author ：wust
 * @date ：Created in 2019/8/8 10:11
 * @description：运营方管理员账号策略，默认能看到所有管辖公司的数据
 * @version:
 */
public class BusinessAdminStrategy implements IStrategy {
    @Override
    public void bindSql(BaseStatementHandler delegate) {
        DefaultBusinessContext ctx = DefaultBusinessContext.getContext();

        BoundSql boundSql = delegate.getBoundSql();

        String sql = boundSql.getSql();

        StringBuffer privilegeSqlStringBuffer = new StringBuffer("SELECT privilege_tmp.* FROM (" + sql + ") privilege_tmp");

        privilegeSqlStringBuffer.append(" WHERE company_id IN (所有下属公司的id)");

        ReflectHelper.setValueByFieldName(boundSql, "sql", privilegeSqlStringBuffer.toString());
    }
}
