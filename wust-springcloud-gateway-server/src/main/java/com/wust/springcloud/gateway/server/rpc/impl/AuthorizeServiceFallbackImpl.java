package com.wust.springcloud.gateway.server.rpc.impl;

import com.wust.springcloud.gateway.server.rpc.AuthorizeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by WST on 2019/4/18.
 */
@Service
public class AuthorizeServiceFallbackImpl implements AuthorizeService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean hasToken(String token) {
        logger.error("调用异常{}",token);
        return false;
    }
}
