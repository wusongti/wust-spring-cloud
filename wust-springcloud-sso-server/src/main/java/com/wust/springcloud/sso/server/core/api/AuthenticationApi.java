package com.wust.springcloud.sso.server.core.api;


import com.alibaba.fastjson.JSONObject;
import com.wust.springcloud.common.annotations.OperationLogAnnotation;
import com.wust.springcloud.common.entity.sys.apptoken.SysAppToken;
import com.wust.springcloud.common.entity.sys.apptoken.SysAppTokenList;
import com.wust.springcloud.common.entity.sys.apptoken.SysAppTokenSearch;
import com.wust.springcloud.common.enums.ApplicationEnum;
import com.wust.springcloud.common.enums.OperationLogEnum;
import com.wust.springcloud.common.util.*;
import com.wust.springcloud.common.util.cache.SpringRedisTools;
import com.wust.springcloud.sso.server.core.service.AuthenticationService;
import io.jsonwebtoken.Claims;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 认证授权控制器
 * Created by WST on 2019/3/15.
 */
@RequestMapping("/AuthenticationApi")
@RestController
public class AuthenticationApi {
    @Autowired
    private SpringRedisTools springRedisTools;


    @Autowired
    private AuthenticationService authenticationServiceImpl;



    /**
     * 根据token获取UserContextDto
     * @param token
     * @return
     */
    @RequestMapping(value = "/getUserContextDtoByToken/{token}")
    public String getUserContextDtoByToken(@PathVariable String token){
        String jsonString = null;
        JSONObject jsonObject = parseJWT(token);
        if(jsonObject == null){
        }else {
            String loginName = jsonObject.getString("loginName");
            String key = String.format(ApplicationEnum.WEB_LOGIN_KEY.getStringValue(), loginName);
            if (springRedisTools.hasKey(key)) {
                Object obj = springRedisTools.getByKey(key);
                jsonString = obj + "";
                return jsonString;
            }
        }
        return jsonString;
    }


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


                /**
                 * 刷新token时间
                 */
                SysAppTokenSearch sysAppTokenSearch = new SysAppTokenSearch();
                sysAppTokenSearch.setLoginName(loginName);
                List<SysAppTokenList> sysAppTokenLists =  authenticationServiceImpl.findByCondition(sysAppTokenSearch);
                if(sysAppTokenLists != null && sysAppTokenLists.size() > 0){
                    SysAppToken sysAppToken = sysAppTokenLists.get(0);
                    sysAppToken.setExpireTime((new DateTime()).plusMinutes(ApplicationEnum.X_AUTH_TOKEN_EXPIRE_TIME.getIntValue()).toDate());
                    authenticationServiceImpl.update(sysAppToken);
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
