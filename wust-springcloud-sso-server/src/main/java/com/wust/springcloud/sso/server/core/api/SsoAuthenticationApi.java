package com.wust.springcloud.sso.server.core.api;

import com.wust.springcloud.common.annotations.OperationLogAnnotation;
import com.wust.springcloud.common.enums.ApplicationEnum;
import com.wust.springcloud.common.enums.OperationLogEnum;
import com.wust.springcloud.common.util.cache.SpringRedisTools;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.concurrent.TimeUnit;

/**
 * @author ：wust
 * @date ：Created in 2019/8/15 9:18
 * @description：
 * @version:
 */
@RequestMapping("/SsoAuthenticationApi")
@RestController
public class SsoAuthenticationApi {
    private static Log logger = LogFactory.getLog(SsoAuthenticationApi.class);

    @Autowired
    private SpringRedisTools springRedisTools;

    /**
     * 校验token
     * @param token
     * @return
     */
    @OperationLogAnnotation(moduleName= OperationLogEnum.MODULE_COMMON,businessName="查询是否存在token",operationType= OperationLogEnum.Search)
    @RequestMapping(value = "/v1/hasToken/{token}")
    public boolean hasToken(@PathVariable String token) {
        String key = String.format(ApplicationEnum.WEB_LOGIN_KEY.getStringValue(),token);
        if(springRedisTools.hasKey(key)){ // 刷新缓存时间和token时间
            /**
             * 刷新redis缓存时间
             */
            springRedisTools.updateExpire(key, ApplicationEnum.X_AUTH_TOKEN_EXPIRE_TIME.getIntValue(), TimeUnit.MINUTES);
            return true;
        }
        logger.error("缓存失效=" + key);
        return false;
    }



    /**
     * 缓存是否已经存在接口签名
     * @param sign
     * @return
     */
    @OperationLogAnnotation(moduleName= OperationLogEnum.MODULE_COMMON,businessName="查询缓存是否已经存在该API签名",operationType= OperationLogEnum.Search)
    @RequestMapping(value = "/v1/hasSign/{sign}")
    public boolean hasSign(@PathVariable String sign) {
        if(springRedisTools.hasKey(sign)){
            return true;
        }else {
            springRedisTools.addData(sign,sign,5, TimeUnit.MINUTES);
            return false;
        }
    }
}
