package com.wust.springcloud.sso.server.core.web.controller;

import com.wust.springcloud.common.annotations.OperationLogAnnotation;
import com.wust.springcloud.common.dto.ResponseDto;
import com.wust.springcloud.common.enums.ApplicationEnum;
import com.wust.springcloud.common.enums.OperationLogEnum;
import com.wust.springcloud.common.util.cache.SpringRedisTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：wust
 * @date ：Created in 2019/7/24 14:01
 * @description：
 * @version:
 */
@RequestMapping("/LogoutController")
@RestController
public class LogoutController {

    @Autowired
    private SpringRedisTools springRedisTools;

    /**
     * 登出
     * @param xAuthToken
     * @return
     */
    @OperationLogAnnotation(moduleName= OperationLogEnum.MODULE_COMMON,businessName="登出",operationType= OperationLogEnum.Logout)
    @RequestMapping(value = "/logout/{xAuthToken}",method = RequestMethod.POST)
    public ResponseDto logout(@PathVariable String xAuthToken) {
        ResponseDto messageMap = new ResponseDto();

        String key = String.format(ApplicationEnum.WEB_LOGIN_KEY.getStringValue(),xAuthToken);
        if(springRedisTools.hasKey(key)){
            springRedisTools.deleteByKey(key);
        }

        return messageMap;
    }
}
