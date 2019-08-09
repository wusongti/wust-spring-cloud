package com.wust.springcloud.admin.server.core.service.impl;


import com.wust.springcloud.admin.server.core.dao.SysUserOrganizationMapper;
import com.wust.springcloud.admin.server.core.service.SysUserOrganizationService;
import com.wust.springcloud.common.dao.IBaseMapper;
import com.wust.springcloud.common.service.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by WST on 2019/4/18.
 */
@Service("sysAppTokenServiceImpl")
public class SysUserOrganizationServiceImpl extends BaseServiceImpl implements SysUserOrganizationService {
    @Autowired
    private SysUserOrganizationMapper sysUserOrganizationMapper;

    @Override
    protected IBaseMapper getBaseMapper() {
        return sysUserOrganizationMapper;
    }
}
