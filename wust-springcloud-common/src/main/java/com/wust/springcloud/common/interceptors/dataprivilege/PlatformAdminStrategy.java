package com.wust.springcloud.common.interceptors.dataprivilege;

import com.wust.springcloud.common.context.DefaultBusinessContext;
import com.wust.springcloud.common.util.ReflectHelper;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.mapping.BoundSql;

/**
 * @author ：wust
 * @date ：Created in 2019/8/8 10:04
 * @description：平台管理员账号类型策略，默认只能看非公司数据
 * @version:
 */
public class PlatformAdminStrategy implements IStrategy {
    @Override
    public void bindSql(BaseStatementHandler delegate) {
        DefaultBusinessContext ctx = DefaultBusinessContext.getContext();

        BoundSql boundSql = delegate.getBoundSql();

        String sql = boundSql.getSql();

        StringBuffer privilegeSqlStringBuffer = new StringBuffer("SELECT privilege_tmp.* FROM (" + sql + ") privilege_tmp");

        privilegeSqlStringBuffer.append(" WHERE company_id IS NULL OR company_id = ''");

        ReflectHelper.setValueByFieldName(boundSql, "sql", privilegeSqlStringBuffer.toString());
    }
}
