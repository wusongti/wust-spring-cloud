package com.wust.springcloud.admin.server.dsaspect;

import com.wust.springcloud.admin.server.core.service.defaults.SysOperationLogService;
import com.wust.springcloud.common.annotations.OperationLogAnnotation;
import com.wust.springcloud.common.context.DefaultBusinessContext;
import com.wust.springcloud.common.entity.sys.operationlog.SysOperationLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import javax.servlet.ServletRequest;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by WST on 2019/5/27.
 */
@Aspect
@Order(1)
@Component
public class OperationLogAspect {
    @Autowired
    private SysOperationLogService sysOperationLogServiceImpl;

    // web前端拦截
    @Pointcut("within(com.wust.springcloud.admin.server.core.web.controller..*)")
    private void webapi(){}

    // 异构系统开放api拦截
    @Pointcut("within(com.wust.springcloud.admin.server.core.openapi..*)")
    private void openapi(){}

    // 同构系统内部rpc拦截
    @Pointcut("within(com.wust.springcloud.admin.server.core.rpc.api..*)")
    private void rpcapi(){}


    //环绕通知
    @Around("webapi() || openapi() || rpcapi()")
    public Object methodAspect(ProceedingJoinPoint jp) throws Throwable {
        Signature sig = jp.getSignature();
        if (sig instanceof MethodSignature) {
            MethodSignature msig = (MethodSignature) sig;
            Method currentMethod = jp.getTarget().getClass().getMethod(msig.getName(), msig.getParameterTypes());

            Object annotationObj = currentMethod.getAnnotation(OperationLogAnnotation.class);
            if(annotationObj != null) {
                OperationLogAnnotation operationLogAnnotation = (OperationLogAnnotation) annotationObj;
                DefaultBusinessContext ctx = DefaultBusinessContext.getContext();
                Object[] args = jp.getArgs();

                SysOperationLog sysOperationLog = new SysOperationLog();
                sysOperationLog.setModuleName(operationLogAnnotation.moduleName().getValue());
                sysOperationLog.setBusinessName(operationLogAnnotation.businessName());
                sysOperationLog.setOperationType(operationLogAnnotation.operationType().getValue());
                sysOperationLog.setCreaterId(ctx.getUserId());
                sysOperationLog.setOperationIp(ctx.getIp());
                sysOperationLog.setOperationData(parseToString(args));
                sysOperationLog.setSource("admin-server");
                sysOperationLog.setCreateTime(new Date());
                sysOperationLogServiceImpl.insert(sysOperationLog);
            }
        }
        return jp.proceed();
    }

    private String parseToString(Object[] args){
        if(args != null && args.length > 0){
            List<Object> list = new ArrayList();
            for (Object arg : args) {
                if (arg instanceof ServletRequest){
                    continue;
                }
                list.add(arg);
            }
            if(list.size() > 0){
                return list.toString();
            }
        }
        return "";
    }
}
