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
        DefaultBusinessContext.getContext().setDataSourceId(ApplicationEnum.DEFAULT.name());
        sysMenuServiceImpl.init();
        sysLookupServiceImpl.init();
        sysDataSourceService.cacheDataSource();
        sysDataPrivilegeServiceImpl.init();
    }

}
