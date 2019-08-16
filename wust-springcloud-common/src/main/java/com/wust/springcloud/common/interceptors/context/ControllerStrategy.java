package com.wust.springcloud.common.interceptors.context;

import com.alibaba.fastjson.JSONObject;
import com.wust.springcloud.common.context.DefaultBusinessContext;
import com.wust.springcloud.common.dto.UserContextDto;
import com.wust.springcloud.common.enums.ApplicationEnum;
import com.wust.springcloud.common.util.MyStringUtils;
import com.wust.springcloud.common.util.SpringContextHolder;
import com.wust.springcloud.common.util.cache.SpringRedisTools;
import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * @author ：wust
 * @date ：Created in 2019/8/8 10:48
 * @description：前端请求走这里设置上下文
 * @version:
 */
public class ControllerStrategy implements IStrategy{
    private static SpringRedisTools springRedisTools;

    @Override
    public void setDefaultBusinessContext(HttpServletRequest request) {

        /**
         * 设置语言
         */
        String localeStr = MyStringUtils.null2String(request.getHeader(ApplicationEnum.X_LOCALE.getStringValue()));
        if (MyStringUtils.isBlank(localeStr)) {
            DefaultBusinessContext.getContext().setLocale(request.getLocale());
        }else{
            Locale locale = new Locale(localeStr.split("-")[0],localeStr.split("-")[1]);
            DefaultBusinessContext.getContext().setLocale(locale);
        }

        /**
         * 设置数据源
         */
        DefaultBusinessContext.getContext().setDataSourceId(ApplicationEnum.DEFAULT.name());


        /**
         * 设置用户信息
         */
        String token = MyStringUtils.null2String(request.getHeader(ApplicationEnum.X_AUTH_TOKEN.getStringValue()));
        if(MyStringUtils.isBlank(token)){
            token = request.getParameter(ApplicationEnum.X_AUTH_TOKEN.getStringValue());
        }

        String key = String.format(ApplicationEnum.WEB_LOGIN_KEY.getStringValue(),token);
        String objStr = getRedisSpringBean().getByKey(key) + "";
        UserContextDto userContextDto = JSONObject.parseObject(objStr, UserContextDto.class);
        if(userContextDto != null){
            DefaultBusinessContext.getContext().setUser(userContextDto.getUser());
        }
    }

    private static SpringRedisTools getRedisSpringBean(){
        if(springRedisTools == null){
            springRedisTools = SpringContextHolder.getBean("springRedisTools");
        }
        return springRedisTools;
    }
}
