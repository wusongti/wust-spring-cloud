package com.wust.springcloud.common.adapter.dataprivilege;

import org.apache.ibatis.executor.statement.BaseStatementHandler;

public interface IStrategy {
    void bindSql(BaseStatementHandler delegate);
}
