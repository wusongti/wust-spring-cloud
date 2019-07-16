package com.wust.springcloud.admin.server.core.service.impl;

import com.wust.springcloud.admin.server.core.service.AuthorizeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by WST on 2019/3/15.
 */
@Service
public class AuthorizeServiceFallbackImpl implements AuthorizeService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean hasToken(String token) {
        logger.error("调用异常{}",token);
        return false;
    }

    @Override
    public String getUserContextDtoByToken(String token) {
        logger.error("调用异常{}",token);
        return null;
    }
}
