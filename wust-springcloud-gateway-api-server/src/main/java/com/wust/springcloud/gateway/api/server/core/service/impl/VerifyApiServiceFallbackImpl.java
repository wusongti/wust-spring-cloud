package com.wust.springcloud.gateway.api.server.core.service.impl;


import com.wust.springcloud.gateway.api.server.core.service.VerifyApiService;

/**
 * @author ：wust
 * @date ：Created in 2019/7/18 14:16
 * @description：
 * @version:
 */
public class VerifyApiServiceFallbackImpl implements VerifyApiService {
    @Override
    public boolean hasSign(String sign) {
        return false;
    }
}
