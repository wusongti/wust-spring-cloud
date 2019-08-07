package com.wust.springcloud.admin.server.initialization;


import com.wust.springcloud.admin.server.core.service.SysDataPrivilegeService;
import com.wust.springcloud.common.annotations.PrivilegeAnnotation;
import com.wust.springcloud.common.entity.sys.dataprivilege.SysDataPrivilege;
import com.wust.springcloud.common.util.cache.SpringRedisTools;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.stereotype.Component;
import java.util.*;

/**
 * @author ：wust
 * @date ：Created in 2019/8/5 10:29
 * @description：
 * @version:
 */
@Component
public class InitializePrivilegeData {

    @Autowired
    private SpringRedisTools springRedisTools;


    @Autowired
    private SysDataPrivilegeService sysDataPrivilegeServiceImpl;

    /**
     * 先根据注解获取到bean，再根据bean获取其注解信息
     * @param applicationReadyEvent
     */
    public void init(ApplicationReadyEvent applicationReadyEvent){
        Map<String, Object> beansWithAnnotationMap = applicationReadyEvent.getApplicationContext().getBeansWithAnnotation(PrivilegeAnnotation.class);
        if(beansWithAnnotationMap != null && beansWithAnnotationMap.size() > 0){
            List<SysDataPrivilege> sysDataPrivilegeList = new ArrayList<>(beansWithAnnotationMap.size());
            Set<Map.Entry<String, Object>> entrySet =  beansWithAnnotationMap.entrySet();
            for (Map.Entry<String, Object> stringObjectEntry : entrySet) {
                PrivilegeAnnotation privilegeAnnotation = AopUtils.getTargetClass(stringObjectEntry.getValue()).getAnnotation(PrivilegeAnnotation.class);
                SysDataPrivilege sysDataPrivilege = new SysDataPrivilege();
                sysDataPrivilege.setUuid(privilegeAnnotation.uuid());
                sysDataPrivilege.setBusinessName(privilegeAnnotation.businessName());
                sysDataPrivilege.setCreateTime(new Date());
                sysDataPrivilegeList.add(sysDataPrivilege);
            }

            sysDataPrivilegeServiceImpl.init(sysDataPrivilegeList);
        }
    }
}
