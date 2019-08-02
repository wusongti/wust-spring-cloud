package com.wust.springcloud.sso.server.core.service.impl;

import com.wust.springcloud.common.dao.IBaseMapper;
import com.wust.springcloud.common.service.BaseServiceImpl;
import com.wust.springcloud.sso.server.core.dao.SysAppTokenMapper;
import com.wust.springcloud.sso.server.core.service.SysAppTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ：wust
 * @date ：Created in 2019/8/2 11:07
 * @description：
 * @version:
 */
@Service("sysAppTokenServiceImpl")
public class SysAppTokenServiceImpl extends BaseServiceImpl implements SysAppTokenService {
    @Autowired
    private SysAppTokenMapper sysAppTokenMapper;



    @Override
    protected IBaseMapper getBaseMapper() {
        return null;
    }
}
