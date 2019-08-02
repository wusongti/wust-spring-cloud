package com.wust.springcloud.sso.server.core.service.impl;

import com.wust.springcloud.common.dao.IBaseMapper;
import com.wust.springcloud.common.service.BaseServiceImpl;
import com.wust.springcloud.sso.server.core.dao.SysUserMapper;
import com.wust.springcloud.sso.server.core.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ：wust
 * @date ：Created in 2019/8/2 11:11
 * @description：
 * @version:
 */
@Service("sysUserServiceImpl")
public class SysUserServiceImpl extends BaseServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    protected IBaseMapper getBaseMapper() {
        return sysUserMapper;
    }
}
