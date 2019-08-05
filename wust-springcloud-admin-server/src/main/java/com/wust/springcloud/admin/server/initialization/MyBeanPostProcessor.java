package com.wust.springcloud.admin.server.initialization;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wust.springcloud.common.annotations.PrivilegeAnnotation;
import com.wust.springcloud.common.entity.sys.dataprivilege.SysDataPrivilege;
import com.wust.springcloud.common.enums.RedisKeyEnum;
import com.wust.springcloud.common.util.cache.SpringRedisTools;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import java.lang.reflect.Method;

/**
 * spring管理的bean初始化完成后执行该类的方法
 * Created by WST on 2019/6/10.
 */
@Component
public class MyBeanPostProcessor implements BeanPostProcessor {
    @Autowired
    private SpringRedisTools springRedisTools;

    @Autowired
    private Environment env;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        String serverName = env.getProperty("spring.application.name");
        String key = String.format(RedisKeyEnum.REDIS_TABLE_KEY_DATA_PRIVILEGE_ANNOTATIONS.getStringValue(),serverName);
        Method[] methods = ReflectionUtils.getAllDeclaredMethods(bean.getClass());
        if (methods != null) {
            for (Method method : methods) {
                PrivilegeAnnotation permissionOperation = AnnotationUtils.findAnnotation(method, PrivilegeAnnotation.class);
                if (null != permissionOperation) {
                    SysDataPrivilege sysDataPrivilege = new SysDataPrivilege();
                    sysDataPrivilege.setUuid(permissionOperation.uuid());
                    sysDataPrivilege.setBusinessName(permissionOperation.businessName());

                    Object obj = springRedisTools.getByKey(key);
                    if(obj != null){
                        JSONArray jsonArray = JSONObject.parseArray(obj.toString());
                        if(!jsonArray.contains(sysDataPrivilege)){
                            jsonArray.add(sysDataPrivilege);
                        }

                        springRedisTools.deleteByKey(key);
                        springRedisTools.addData(key,jsonArray.toJSONString());
                    }else{
                        JSONArray jsonArray = new JSONArray(1);
                        jsonArray.add(sysDataPrivilege);
                        springRedisTools.addData(key,jsonArray.toJSONString());
                    }
                }
            }
        }
        return bean;
    }
}
