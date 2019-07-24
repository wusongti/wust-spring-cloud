package com.wust.springcloud.admin.server.listener;

import com.alibaba.fastjson.JSONObject;
import com.wust.springcloud.admin.server.core.service.SysDataPrivilegeService;
import com.wust.springcloud.admin.server.core.service.SysDataSourceService;
import com.wust.springcloud.admin.server.core.service.SysLookupService;
import com.wust.springcloud.admin.server.core.service.SysMenuService;
import com.wust.springcloud.common.context.DefaultBusinessContext;
import com.wust.springcloud.common.enums.ApplicationEnum;
import com.wust.springcloud.common.enums.RedisKeyEnum;
import com.wust.springcloud.common.util.MyStringUtils;
import com.wust.springcloud.common.util.cache.SpringRedisTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Created by WST on 2019/6/12.
 */
@Component
public class MyApplicationListener implements ApplicationListener<ApplicationReadyEvent> {
    @Autowired
    private SysDataPrivilegeService sysDataPrivilegeServiceImpl;

    @Autowired
    private SysMenuService sysMenuServiceImpl;

    @Autowired
    private SysLookupService sysLookupServiceImpl;

    @Autowired
    private SpringRedisTools springRedisTools;

    @Autowired
    private Environment env;


    @Autowired
    private SysDataSourceService sysDataSourceService;


    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        /**
         * 默认初始化平台库数据
         */
        DefaultBusinessContext.getContext().setDataSourceId(ApplicationEnum.DEFAULT.name());
        sysMenuServiceImpl.init();
        sysLookupServiceImpl.init();
        sysDataSourceService.cacheDataSource();
        sysDataPrivilegeServiceImpl.init();


        /**
         * 为所有公司库初始化数据
         */
        Object obj = springRedisTools.getByKey(RedisKeyEnum.REDIS_TABLE_KEY_DATA_SOURCE.name());
        if(obj != null) {
            JSONObject jsonObject = JSONObject.parseObject(obj.toString());
            for (String dataSourceId : jsonObject.keySet()) {
                if (MyStringUtils.isBlank(MyStringUtils.null2String(dataSourceId))) {
                    continue;
                }

                // 切换数据源
                DefaultBusinessContext.getContext().setDataSourceId(dataSourceId);
                DefaultBusinessContext.getContext().setCompanyId(dataSourceId);

                sysMenuServiceImpl.init();
                sysLookupServiceImpl.init();
                sysDataPrivilegeServiceImpl.init();
            }
        }
    }

}
