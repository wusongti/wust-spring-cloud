package com.wust.springcloud.sso.server.datasource;


import com.wust.springcloud.common.context.DefaultBusinessContext;
import com.wust.springcloud.common.entity.sys.datasource.SysDataSourceList;
import com.wust.springcloud.common.enums.ApplicationEnum;
import com.wust.springcloud.common.exception.BusinessException;
import com.wust.springcloud.common.util.MyStringUtils;
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
        String dataSourceId = MyStringUtils.isBlank(MyStringUtils.null2String(ctx.getDataSourceId())) ? ApplicationEnum.DEFAULT.name() : ctx.getDataSourceId();
        lookupAndCreateDataSource(dataSourceId);
        return dataSourceId;
    }

    private void lookupAndCreateDataSource(String dataSourceId) {
        if(!dataSourceMap.containsKey(dataSourceId)){
            SysDataSourceList sysDataSourceList =  DataDictionaryUtil.getDataSourceById(dataSourceId);
            if(sysDataSourceList != null){
                createDataSource(dataSourceId,sysDataSourceList.getJdbcUrl(),sysDataSourceList.getJdbcUsername(),sysDataSourceList.getJdbcPassword(),sysDataSourceList.getJdbcDriver());
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
