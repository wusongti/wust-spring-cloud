package com.wust.springcloud.common.interceptors.dataprivilege;

import com.wust.springcloud.common.context.DefaultBusinessContext;
import org.apache.ibatis.executor.statement.BaseStatementHandler;

/**
 * @author ：wust
 * @date ：Created in 2019/8/8 10:04
 * @description：
 * @version:
 */
public class StrategyContext {
    private StrategyContext(){}
    private static final StrategyContext instance = new StrategyContext();
    public static StrategyContext getInstance(){
        return instance;
    }

    public void bindSql(BaseStatementHandler delegate) {
        DefaultBusinessContext ctx = DefaultBusinessContext.getContext();
        if("100401".equals(ctx.getUserType()) || "100402".equals(ctx.getUserType())){ // 平台管理员，不对其进行数据权限过滤
            IStrategy iStrategy = new PlatformAdminStrategy();
            iStrategy.bindSql(delegate);
        }else if("100403".equals(ctx.getUserType())){ // 运营方账号
            IStrategy iStrategy = new BusinessAdminStrategy();
            iStrategy.bindSql(delegate);
        }else if("100404".equals(ctx.getUserType())){ // 操作方账号
            IStrategy iStrategy = new BusinessUserStrategy();
            iStrategy.bindSql(delegate);
        }
    }
}