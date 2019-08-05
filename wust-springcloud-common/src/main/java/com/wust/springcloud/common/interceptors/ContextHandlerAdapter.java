package com.wust.springcloud.common.interceptors;

import com.alibaba.fastjson.JSONObject;
import com.wust.springcloud.common.context.DefaultBusinessContext;
import com.wust.springcloud.common.dto.UserContextDto;
import com.wust.springcloud.common.enums.ApplicationEnum;
import com.wust.springcloud.common.util.JwtHelper;
import com.wust.springcloud.common.util.MyStringUtils;
import com.wust.springcloud.common.util.cache.SpringRedisTools;
import io.jsonwebtoken.Claims;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.Map;

/**
 * Created by WST on 2019/6/17.
 */
public abstract class ContextHandlerAdapter {
    static Logger logger = LogManager.getLogger(ContextHandlerAdapter.class);

    @Autowired
    private SpringRedisTools springRedisTools;

    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String token = MyStringUtils.null2String(httpServletRequest.getHeader(ApplicationEnum.X_AUTH_TOKEN.getStringValue()));
        if(MyStringUtils.isNotBlank(token)){ // 前端网页请求
            String jsonString = getUserContextDtoByToken(token);
            if(MyStringUtils.isBlank(MyStringUtils.null2String(jsonString))){
                logger.error("该请求是非法的，原因是没有登录系统或者登录信息已经过期");
                return false;
            }

            UserContextDto userContextDto = JSONObject.parseObject(jsonString,UserContextDto.class);
            setDefaultBusinessContext(userContextDto,httpServletRequest);
        }else{ // 开放api请求
            setDefaultBusinessContext(httpServletRequest);
        }
        return true;
    }

    /**
     * 内部系统web前端请求，设置上下文
     * @param userContextDto
     * @param httpServletRequest
     */
    private static void setDefaultBusinessContext(UserContextDto userContextDto,HttpServletRequest httpServletRequest){
        String localeStr = MyStringUtils.null2String(httpServletRequest.getHeader(ApplicationEnum.X_LOCALE.getStringValue()));
        if (MyStringUtils.isBlank(localeStr)) {
            DefaultBusinessContext.getContext().setLocale(httpServletRequest.getLocale());
        }else{
            Locale locale = new Locale(localeStr.split("-")[0],localeStr.split("-")[1]);
            DefaultBusinessContext.getContext().setLocale(locale);
        }


        if(userContextDto != null){
            DefaultBusinessContext.getContext().setUserId(userContextDto.getUser().getId());
            DefaultBusinessContext.getContext().setLoginName(userContextDto.getUser().getLoginName());
            DefaultBusinessContext.getContext().setUserType(userContextDto.getUser().getType());
            DefaultBusinessContext.getContext().setCompanyId(userContextDto.getUser().getCompanyId());
        }


        // 目前暂时用不到切换数据源，所有用户的数据源一样
        DefaultBusinessContext.getContext().setDataSourceId(ApplicationEnum.DEFAULT.name());
    }

    /**
     * 外部系统api请求，设置上下文
     * @param httpServletRequest
     */
    private static void setDefaultBusinessContext(HttpServletRequest httpServletRequest){
        String jsonStr = MyStringUtils.null2String(httpServletRequest.getHeader(ApplicationEnum.API_SIGN.getStringValue()));
        Map paraMap = JSONObject.parseObject(jsonStr, Map.class);

        String localeStr = MyStringUtils.null2String(httpServletRequest.getHeader(ApplicationEnum.X_LOCALE.getStringValue()));
        if (MyStringUtils.isBlank(localeStr)) {
            DefaultBusinessContext.getContext().setLocale(httpServletRequest.getLocale());
        }else{
            Locale locale = new Locale(localeStr.split("-")[0],localeStr.split("-")[1]);
            DefaultBusinessContext.getContext().setLocale(locale);
        }

        DefaultBusinessContext.getContext().setSignMap(paraMap);
        DefaultBusinessContext.getContext().setDataSourceId(ApplicationEnum.DEFAULT.name());
        DefaultBusinessContext.getContext().setCompanyId("companyId");
    }


    private String getUserContextDtoByToken(String token) {
        JSONObject jsonObject = parseJWT(token);
        if(jsonObject == null){
        }else {
            String loginName = jsonObject.getString("loginName");
            String key = String.format(ApplicationEnum.WEB_LOGIN_KEY.getStringValue(), loginName);
            if (springRedisTools.hasKey(key)) {
                Object obj = springRedisTools.getByKey(key);
                return obj + "";
            }
        }
        return null;
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
