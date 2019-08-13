package com.wust.springcloud.gateway.api.server.core.service.impl;


import com.wust.springcloud.gateway.api.server.core.service.SsoService;

/**
 * @author ：wust
 * @date ：Created in 2019/7/18 14:16
 * @description：
 * @version:
 */
public class SsoServiceFallbackImpl implements SsoService {
    @Override
    public boolean hasSign(String sign) {
        return false;
    }
}
