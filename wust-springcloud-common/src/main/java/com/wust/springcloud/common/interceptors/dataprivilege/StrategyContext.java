package com.wust.springcloud.common.interceptors.dataprivilege;

import com.wust.springcloud.common.context.DefaultBusinessContext;
import com.wust.springcloud.common.enums.DataDictionaryEnum;
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
        if(DataDictionaryEnum.USER_TYPE_PLATFORM_ADMIN.getStringValue().equals(ctx.getUserType())){ // 平台管理员
            IStrategy iStrategy = new PlatformAdminStrategy();
            iStrategy.bindSql(delegate);
        }else if(DataDictionaryEnum.USER_TYPE_PLATFORM_USER.getStringValue().equals(ctx.getUserType())){ // 平台普通用户
            IStrategy iStrategy = new PlatformUserStrategy();
            iStrategy.bindSql(delegate);
        }else if(DataDictionaryEnum.USER_TYPE_AGENT.getStringValue().equals(ctx.getUserType())
                || DataDictionaryEnum.USER_TYPE_PARENT_COMPANY.getStringValue().equals(ctx.getUserType())
                || DataDictionaryEnum.USER_TYPE_BRANCH_COMPANY.getStringValue().equals(ctx.getUserType())){ // 运营方账号
            IStrategy iStrategy = new BusinessAdminStrategy();
            iStrategy.bindSql(delegate);
        }else if(DataDictionaryEnum.USER_TYPE_BUSINESS.getStringValue().equals(ctx.getUserType())){ // 操作方账号
            IStrategy iStrategy = new BusinessUserStrategy();
            iStrategy.bindSql(delegate);
        }
    }
}
