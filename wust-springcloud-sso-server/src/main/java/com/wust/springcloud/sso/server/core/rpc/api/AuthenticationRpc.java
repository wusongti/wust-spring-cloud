package com.wust.springcloud.sso.server.core.rpc.api;


import com.alibaba.fastjson.JSONObject;
import com.wust.springcloud.common.annotations.OperationLogAnnotation;
import com.wust.springcloud.common.enums.ApplicationEnum;
import com.wust.springcloud.common.enums.OperationLogEnum;
import com.wust.springcloud.common.util.*;
import com.wust.springcloud.common.util.cache.SpringRedisTools;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 同构系统接口：认证授权控制器
 * Created by WST on 2019/3/15.
 */
@RequestMapping("/AuthenticationRpc")
@RestController
public class AuthenticationRpc {
    @Autowired
    private SpringRedisTools springRedisTools;




    /**
     * 校验token
     * @param token
     * @return
     */
    @OperationLogAnnotation(moduleName= OperationLogEnum.MODULE_COMMON,businessName="查询是否存在token",operationType= OperationLogEnum.Search)
    @RequestMapping(value = "/hasToken/{token}")
    public boolean hasToken(@PathVariable String token) {
        boolean flag = true;
        JSONObject jsonObject = parseJWT(token);
        if(jsonObject == null){
            flag = false;
        }else{
            String loginName = jsonObject.getString("loginName");
            String key = String.format(ApplicationEnum.WEB_LOGIN_KEY.getStringValue(),loginName);
            if(springRedisTools.hasKey(key)){ // 刷新缓存时间和token时间
                /**
                 * 刷新redis缓存时间
                 */
                Object obj = springRedisTools.getByKey(key);
                if(obj != null){
                    springRedisTools.updateExpire(key, ApplicationEnum.X_AUTH_TOKEN_EXPIRE_TIME.getIntValue(), TimeUnit.MINUTES);
                }else{
                    flag = false;
                    return flag;
                }
                flag = true;
            }else{
                flag = false;
            }
        }
        return flag;
    }





    private JSONObject parseJWT(String token){
        JSONObject jsonObject = null;
        try {
            Claims claims = JwtHelper.parseJWT(ApplicationEnum.JWT_ACCESS_SECRET.getStringValue(),token);
            jsonObject = JSONObject.parseObject(claims.getSubject());
        } catch (Exception e) {
        }
        return jsonObject;
    }
}
