package com.wust.springcloud.common.interceptors.context;

import com.alibaba.fastjson.JSONObject;
import com.wust.springcloud.common.context.DefaultBusinessContext;
import com.wust.springcloud.common.enums.ApplicationEnum;
import com.wust.springcloud.common.util.MyStringUtils;
import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.Map;

/**
 * @author ：wust
 * @date ：Created in 2019/8/8 10:47
 * @description：开放api请求走这里设置上下文
 * @version:
 */
public class OpenApiStrategy implements IStrategy{
    @Override
    public void setDefaultBusinessContext(HttpServletRequest request) {
        String jsonStr = MyStringUtils.null2String(request.getHeader(ApplicationEnum.API_SIGN.getStringValue()));
        Map paraMap = JSONObject.parseObject(jsonStr, Map.class);

        String localeStr = MyStringUtils.null2String(request.getHeader(ApplicationEnum.X_LOCALE.getStringValue()));
        if (MyStringUtils.isBlank(localeStr)) {
            DefaultBusinessContext.getContext().setLocale(request.getLocale());
        }else{
            Locale locale = new Locale(localeStr.split("-")[0],localeStr.split("-")[1]);
            DefaultBusinessContext.getContext().setLocale(locale);
        }

        DefaultBusinessContext.getContext().setSignMap(paraMap);
        DefaultBusinessContext.getContext().setDataSourceId(ApplicationEnum.DEFAULT.name());
        DefaultBusinessContext.getContext().setCompanyId(paraMap.get("companyId")+"");
    }
}
