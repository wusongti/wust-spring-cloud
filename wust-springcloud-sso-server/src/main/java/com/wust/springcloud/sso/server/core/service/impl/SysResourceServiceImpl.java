package com.wust.springcloud.sso.server.core.service.impl;

import com.wust.springcloud.common.dao.IBaseMapper;
import com.wust.springcloud.common.entity.sys.resource.SysResource;
import com.wust.springcloud.common.service.BaseServiceImpl;
import com.wust.springcloud.sso.server.core.dao.SysResourceMapper;
import com.wust.springcloud.sso.server.core.service.SysResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author ：wust
 * @date ：Created in 2019/8/2 11:10
 * @description：
 * @version:
 */
@Service("sysResourceServiceImpl")
public class SysResourceServiceImpl extends BaseServiceImpl implements SysResourceService {
    @Autowired
    private SysResourceMapper sysResourceMapper;

    @Override
    protected IBaseMapper getBaseMapper() {
        return sysResourceMapper;
    }

    @Override
    public List<SysResource> findAllResources4systemAdmin() {
        return sysResourceMapper.findAllResources4systemAdmin();
    }

    @Override
    public List<SysResource> findAllAnonResources4systemAdmin() {
        return sysResourceMapper.findAllAnonResources4systemAdmin();
    }

    @Override
    public List<SysResource> findResourcesByUserId(Long userId) {
        return sysResourceMapper.findResourcesByUserId(userId);
    }

    @Override
    public List<SysResource> findAnonResourcesByUserId(Long userId) {
        return sysResourceMapper.findAnonResourcesByUserId(userId);
    }
}
