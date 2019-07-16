package com.wust.springcloud.common.interceptors;

import com.alibaba.fastjson.JSONObject;
import com.wust.springcloud.common.context.DefaultBusinessContext;
import com.wust.springcloud.common.dto.UserContextDto;
import com.wust.springcloud.common.enums.ApplicationEnum;
import com.wust.springcloud.common.util.MyStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by WST on 2019/6/17.
 */
public abstract class RealmHandlerAdapter {
    static Logger logger = LogManager.getLogger(RealmHandlerAdapter.class);

    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String token = MyStringUtils.null2String(httpServletRequest.getHeader(ApplicationEnum.X_AUTH_TOKEN.getStringValue()));
        if(MyStringUtils.isBlank(token)){
            if(httpServletRequest.getParameterMap().containsKey(ApplicationEnum.X_AUTH_TOKEN.getStringValue())){
                token = httpServletRequest.getParameter(ApplicationEnum.X_AUTH_TOKEN.getStringValue());
            }

            if(MyStringUtils.isBlank(token)){
                logger.error("该请求是非法的，原因是没有登录系统或者登录信息已经过期");
                return false;
            }
        }

        boolean flag = hasToken(token);
        if(!flag){
            logger.error("该请求是非法的，原因是没有登录系统或者登录信息已经过期");
            return false;
        }
        String jsonString = getUserContextDtoByToken(token);
        if(MyStringUtils.isBlank(MyStringUtils.null2String(jsonString))){
            logger.error("该请求是非法的，原因是没有登录系统或者登录信息已经过期");
            return false;
        }

        UserContextDto userContextDto = JSONObject.parseObject(jsonString,UserContextDto.class);
        setDefaultBusinessContext(userContextDto,httpServletRequest);
        return true;
    }

    private static void setDefaultBusinessContext(UserContextDto userContextDto,HttpServletRequest httpServletRequest){
        DefaultBusinessContext.getContext().setLocale(httpServletRequest.getLocale());
        DefaultBusinessContext.getContext().setUserId(userContextDto.getUser().getId());
        DefaultBusinessContext.getContext().setLoginName(userContextDto.getUser().getLoginName());
        DefaultBusinessContext.getContext().setUserType(userContextDto.getUser().getType());
        DefaultBusinessContext.getContext().setCompanyId(userContextDto.getUser().getCompanyId());
        DefaultBusinessContext.getContext().setDataSourceId(userContextDto.getUser().getCompanyId());
    }

    protected abstract boolean hasToken(String token);

    protected abstract String getUserContextDtoByToken(String token);
}
