package com.wust.springcloud.common.aspects;


import com.wust.springcloud.common.annotations.OperationLogAnnotation;
import com.wust.springcloud.common.context.DefaultBusinessContext;
import com.wust.springcloud.common.entity.sys.operationlog.SysOperationLog;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by WST on 2019/5/27.
 */

public abstract class BaseOperationLogAspect {
    private static Log logger = LogFactory.getLog(BaseOperationLogAspect.class);


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
                sysOperationLog.setOperationIp(getIpAddress(ctx.getRequest()));
                sysOperationLog.setOperationData(parse2string(args));
                sysOperationLog.setSource(getServerName());
                sysOperationLog.setCompanyId(ctx.getCompanyId());
                sysOperationLog.setCreaterId(ctx.getUserId());
                sysOperationLog.setCreaterName(ctx.getRealName());
                sysOperationLog.setCreateTime(new Date());
                insert(sysOperationLog);
            }
        }
        return jp.proceed();
    }

    protected abstract String getServerName();

    protected abstract void insert(SysOperationLog sysOperationLog);


    private String parse2string(Object[] args){
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


    private static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        logger.info("x-forwarded-for ip: " + ip);
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
            logger.info("Proxy-Client-IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
            logger.info("WL-Proxy-Client-IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
            logger.info("HTTP_CLIENT_IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            logger.info("HTTP_X_FORWARDED_FOR ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
            logger.info("X-Real-IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            logger.info("getRemoteAddr ip: " + ip);
        }

        if("127.0.0.1".equals(ip)){
            String ip1 = getHostIp();
            if(ip1 != null){
                return ip1;
            }
        }
        return ip;
    }


    private static String getHostIp(){
        try{
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            while (allNetInterfaces.hasMoreElements()){
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                while (addresses.hasMoreElements()){
                    InetAddress ip = (InetAddress) addresses.nextElement();
                    if (ip != null
                            && ip instanceof Inet4Address
                            && !ip.isLoopbackAddress() //loopback地址即本机地址，IPv4的loopback范围是127.0.0.0 ~ 127.255.255.255
                            && ip.getHostAddress().indexOf(":")==-1){
                        return ip.getHostAddress();
                    }
                }
            }
        }catch(Exception e){
            logger.error("获取本机IP地址出错",e);
        }
        return null;
    }
}
