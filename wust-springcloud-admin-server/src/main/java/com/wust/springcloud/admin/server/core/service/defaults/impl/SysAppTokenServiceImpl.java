package com.wust.springcloud.admin.server.core.service.defaults.impl;

import com.wust.springcloud.admin.server.core.dao.SysAppTokenMapper;
import com.wust.springcloud.admin.server.core.service.defaults.SysAppTokenService;
import com.wust.springcloud.common.dao.IBaseMapper;
import com.wust.springcloud.common.service.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by WST on 2019/4/18.
 */
@Service("sysAppTokenServiceImpl")
public class SysAppTokenServiceImpl  extends BaseServiceImpl implements SysAppTokenService {
    @Autowired
    private SysAppTokenMapper sysAppTokenMapper;

    @Override
    protected IBaseMapper getBaseMapper() {
        return sysAppTokenMapper;
    }
}
