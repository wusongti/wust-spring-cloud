package com.wust.springcloud.sso.server.core.web.api;

import com.alibaba.fastjson.JSONObject;
import com.wust.springcloud.common.annotations.OperationLogAnnotation;
import com.wust.springcloud.common.dto.ResponseDto;
import com.wust.springcloud.common.entity.sys.apptoken.SysAppToken;
import com.wust.springcloud.common.entity.sys.apptoken.SysAppTokenList;
import com.wust.springcloud.common.entity.sys.apptoken.SysAppTokenSearch;
import com.wust.springcloud.common.enums.ApplicationEnum;
import com.wust.springcloud.common.enums.OperationLogEnum;
import com.wust.springcloud.common.util.JwtHelper;
import com.wust.springcloud.common.util.MyStringUtils;
import com.wust.springcloud.common.util.RC4;
import com.wust.springcloud.common.util.cache.SpringRedisTools;
import com.wust.springcloud.sso.server.core.service.AuthenticationService;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.TimeUnit;


@RequestMapping("/LoginApi")
@RestController
public class LoginApi {
    @Autowired
    private SpringRedisTools springRedisTools;

    @Autowired
    private AuthenticationService authenticationServiceImpl;

    /**
     * 外部系统登录获取token
     * @param jsonObject
     * @return
     */
    @OperationLogAnnotation(moduleName= OperationLogEnum.MODULE_COMMON,businessName="外部登录",operationType= OperationLogEnum.Login)
    @RequestMapping(value = "/login4api",method = RequestMethod.POST)
    public ResponseDto login(@RequestBody JSONObject jsonObject) {
        ResponseDto messageMap = new ResponseDto();

        String loginName = jsonObject.getString("loginName");
        String password = jsonObject.getString("password");

        if(MyStringUtils.isBlank(MyStringUtils.null2String(loginName))){
            messageMap.setFlag(ResponseDto.INFOR_WARNING);
            messageMap.setMessage("请输入登录账号");
            return messageMap;
        }

        if(MyStringUtils.isBlank(MyStringUtils.null2String(password))){
            messageMap.setFlag(ResponseDto.INFOR_WARNING);
            messageMap.setMessage("请输入登录口令");
            return messageMap;
        }

        String passwordRC4 = Base64.encodeBase64String(RC4.encry_RC4_string(password, ApplicationEnum.RC4_LOGIN_PASSWORD.getStringValue()).getBytes());
        SysAppTokenSearch sysAppTokenSearch = new SysAppTokenSearch();
        sysAppTokenSearch.setLoginName(loginName);
        sysAppTokenSearch.setPassword(passwordRC4);
        List<SysAppTokenList> sysAppTokenLists =  authenticationServiceImpl.findByCondition(sysAppTokenSearch);
        if(sysAppTokenLists != null && sysAppTokenLists.size() > 0){
            JSONObject subJSONObject = new JSONObject();
            subJSONObject.put("loginName",loginName);
            String token = createJWT(subJSONObject.toJSONString());
            if (StringUtils.isEmpty(MyStringUtils.null2String(token))) {
                messageMap.setFlag(ResponseDto.INFOR_WARNING);
                messageMap.setMessage("登录失败");
            }else{
                String key = String.format(ApplicationEnum.WEB_LOGIN_KEY.getStringValue(),loginName);
                springRedisTools.addData(key, key,  ApplicationEnum.X_AUTH_TOKEN_EXPIRE_TIME.getIntValue(), TimeUnit.MINUTES);

                SysAppToken sysAppToken = sysAppTokenLists.get(0);
                sysAppToken.setToken(token);
                sysAppToken.setExpireTime((new DateTime()).plusMinutes(ApplicationEnum.X_AUTH_TOKEN_EXPIRE_TIME.getIntValue()).toDate());
                authenticationServiceImpl.update(sysAppToken);
            }

            messageMap.setObj(token);
        }else{
            messageMap.setFlag(ResponseDto.INFOR_WARNING);
            messageMap.setMessage("账号或密码错误");
            return messageMap;
        }
        return messageMap;
    }

    private String createJWT(String subject) {
        String token = null;
        try {
            token = JwtHelper.createJWT(ApplicationEnum.JWT_ACCESS_SECRET.getStringValue(),subject,-1);
        } catch (Exception e) {
        }
        return token;
    }
}
