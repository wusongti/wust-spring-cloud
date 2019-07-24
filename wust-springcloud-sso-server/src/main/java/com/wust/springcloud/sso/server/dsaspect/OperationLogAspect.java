package com.wust.springcloud.sso.server.dsaspect;

import com.wust.springcloud.common.annotations.OperationLogAnnotation;
import com.wust.springcloud.common.context.DefaultBusinessContext;
import com.wust.springcloud.common.entity.sys.operationlog.SysOperationLog;
import com.wust.springcloud.common.enums.ApplicationEnum;
import com.wust.springcloud.sso.server.core.service.SysOperationLogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import javax.servlet.ServletRequest;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by WST on 2019/5/27.
 */
@Aspect
@Order(1)
@Component
public class OperationLogAspect {
    @Autowired
    private SysOperationLogService sysOperationLogServiceImpl;

    //环绕通知
    @Around("execution(* com.wust.springcloud.sso.server.core.api..*.*(..))")
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

                List<SysOperationLog> sysOperationLogs = new ArrayList<>(1);
                SysOperationLog sysOperationLog = new SysOperationLog();
                sysOperationLog.setModuleName(operationLogAnnotation.moduleName().getValue());
                sysOperationLog.setBusinessName(operationLogAnnotation.businessName());
                sysOperationLog.setOperationType(operationLogAnnotation.operationType().getValue());
                sysOperationLog.setCreaterId(ctx.getUserId());
                sysOperationLog.setOperationIp(ctx.getIp());
                sysOperationLog.setOperationData(parseToString(args));
                sysOperationLog.setSource("sso-server");
                sysOperationLogs.add(sysOperationLog);
                DefaultBusinessContext.getContext().setDataSourceId(ApplicationEnum.DEFAULT.name());
                sysOperationLogServiceImpl.batchInsert(sysOperationLogs);
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
