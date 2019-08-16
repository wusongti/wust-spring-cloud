package com.wust.springcloud.admin.server.aspect;

import com.wust.springcloud.admin.server.core.service.SysOperationLogService;
import com.wust.springcloud.common.aspects.BaseOperationLogAspect;
import com.wust.springcloud.common.entity.sys.operationlog.SysOperationLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created by WST on 2019/5/27.
 */
@Aspect
@Order(1)
@Component
public class OperationLogAspect extends BaseOperationLogAspect {
    @Autowired
    private SysOperationLogService sysOperationLogServiceImpl;

    // controller拦截
    @Pointcut("within(com.wust.springcloud.admin.server.core.web.controller..*)")
    private void controller(){}

    // 异构系统开放api拦截
    @Pointcut("within(com.wust.springcloud.admin.server.core.openapi..*)")
    private void openapi(){}

    // 同构系统内部api拦截
    @Pointcut("within(com.wust.springcloud.admin.server.core.api..*)")
    private void api(){}



    //环绕通知
    @Around("controller() || openapi() || api()")
    @Override
    public Object methodAspect(ProceedingJoinPoint jp) throws Throwable {
        return super.methodAspect(jp);
    }

    @Override
    protected String getServerName() {
        return "admin-server";
    }

    @Override
    protected void insert(SysOperationLog sysOperationLog) {
        sysOperationLogServiceImpl.insert(sysOperationLog);
    }
}
