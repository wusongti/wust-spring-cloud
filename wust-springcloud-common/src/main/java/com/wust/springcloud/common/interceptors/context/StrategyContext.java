package com.wust.springcloud.common.interceptors.context;

import com.wust.springcloud.common.enums.ApplicationEnum;
import com.wust.springcloud.common.exception.BusinessException;
import com.wust.springcloud.common.util.MyStringUtils;
import javax.servlet.http.HttpServletRequest;

/**
 * @author ：wust
 * @date ：Created in 2019/8/8 10:04
 * @description：
 * @version:
 */
public class StrategyContext {
    private StrategyContext(){}
    private static final StrategyContext instance = new StrategyContext();
    public static StrategyContext getInstance(){
        return instance;
    }

    public void setDefaultBusinessContext(HttpServletRequest request) {

        String token = MyStringUtils.null2String(request.getHeader(ApplicationEnum.X_AUTH_TOKEN.getStringValue()));

        if(MyStringUtils.isBlank(token)){
            token = request.getParameter(ApplicationEnum.X_AUTH_TOKEN.getStringValue());
        }

        if(MyStringUtils.isBlank(token)){
            String jsonStr = MyStringUtils.null2String(request.getHeader(ApplicationEnum.API_SIGN.getStringValue()));
            if(MyStringUtils.isBlank(jsonStr)){
                throw new BusinessException("非法的请求");
            }

            /**
             * 开放api请求
             */
            IStrategy iStrategy = new OpenApiStrategy();
            iStrategy.setDefaultBusinessContext(request);
        }else{
            /**
             * web前端请求
             */
            IStrategy iStrategy = new ControllerStrategy();
            iStrategy.setDefaultBusinessContext(request);
        }
    }
}
