package com.wust.springcloud.common.interceptors.dataprivilege;

import com.alibaba.fastjson.JSONArray;
import com.wust.springcloud.common.context.DefaultBusinessContext;
import com.wust.springcloud.common.enums.RedisKeyEnum;
import com.wust.springcloud.common.util.MyStringUtils;
import com.wust.springcloud.common.util.ReflectHelper;
import com.wust.springcloud.common.util.SpringContextHolder;
import com.wust.springcloud.common.util.cache.SpringRedisTools;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.mapping.BoundSql;

import java.util.List;

/**
 * @author ：wust
 * @date ：Created in 2019/8/8 10:11
 * @description：运营方管理员账号策略，默认能看到所有管辖公司的数据
 * @version:
 */
public class BusinessAdminStrategy implements IStrategy {
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

        Object obj = getRedisSpringBean().getByKey(String.format(RedisKeyEnum.REDIS_TABLE_KEY_CURRENT_USER_BRANCH_COMPANY_ID.getStringValue(),ctx.getUser().getId()));
        if(obj != null){
            List<String> list = JSONArray.parseArray(obj.toString(),String.class);
            if(CollectionUtils.isNotEmpty(list)){
                String companyIds = "";
                for (String s : list) {
                    if(MyStringUtils.isBlank(MyStringUtils.null2String(s))){
                        continue;
                    }
                    if(MyStringUtils.isNotBlank(companyIds)){
                        companyIds += "," + s;
                    }else{
                        companyIds +=  s;
                    }
                }
                privilegeSqlStringBuffer.append(" WHERE company_id IN (" + companyIds + ") OR creater_id = " + ctx.getUser().getId());
            }
        }

        ReflectHelper.setValueByFieldName(boundSql, "sql", privilegeSqlStringBuffer.toString());
    }
}
