package com.wust.springcloud.common;


import com.wust.springcloud.common.context.DefaultBusinessContext;
import com.wust.springcloud.common.entity.sys.datasource.SysDataSource;
import com.wust.springcloud.common.exception.BusinessException;
import com.wust.springcloud.common.util.cache.DataDictionaryUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by WST on 2019/6/20.
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    private static Log logger = LogFactory.getLog(DynamicDataSource.class);

    private static Map<Object, Object> dataSourceMap = new ConcurrentHashMap<>();

    @Override
    protected Object determineCurrentLookupKey() {
        DefaultBusinessContext ctx = DefaultBusinessContext.getContext();
        if(ctx.getDataSourceId() == null){
            return null;
        }

        lookupAndCreateDataSource(ctx.getDataSourceId());
        return ctx.getDataSourceId();
    }

    private void lookupAndCreateDataSource(String dataSourceId) {
        if(dataSourceMap.containsKey(dataSourceId)){
            SysDataSource sysDataSource =  DataDictionaryUtil.getDataSourceById(dataSourceId);
            if(sysDataSource != null){
                createDataSource(dataSourceId,sysDataSource.getJdbcUrl(),sysDataSource.getJdbcUsername(),sysDataSource.getJdbcPassword(),sysDataSource.getJdbcDriver());
            }else{
                logger.error("创建数据源失败，缓存里面没有发现数据源，数据源ID["+dataSourceId+"]");
                throw new BusinessException("创建数据源失败，缓存里面没有发现数据源，数据源ID["+dataSourceId+"]");
            }
        }
    }


    public DataSource createDataSource(String id, String url, String username, String password, String driverClassName) {
        DataSource dataSource = DataSourceBuilder.create()
                .url(url)
                .username(username)
                .password(password)
                .driverClassName(driverClassName)
                .build();
        if (dataSource != null) {
            dataSourceMap.put(id, dataSource);

            super.setTargetDataSources(dataSourceMap);
            super.afterPropertiesSet();
            return dataSource;
        }
        return null;
    }

}
