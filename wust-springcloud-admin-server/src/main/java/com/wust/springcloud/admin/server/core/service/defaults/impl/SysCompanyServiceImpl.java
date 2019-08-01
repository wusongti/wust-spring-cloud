package com.wust.springcloud.admin.server.core.service.defaults.impl;

import com.wust.springcloud.admin.server.core.dao.SysCompanyMapper;
import com.wust.springcloud.admin.server.core.service.defaults.SysCompanyService;
import com.wust.springcloud.common.dao.IBaseMapper;
import com.wust.springcloud.common.service.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by WST on 2019/6/3.
 */
@Service("sysCompanyServiceImpl")
public class SysCompanyServiceImpl  extends BaseServiceImpl implements SysCompanyService {
    @Autowired
    private SysCompanyMapper sysCompanyMapper;


    @Override
    protected IBaseMapper getBaseMapper() {
        return sysCompanyMapper;
    }
}
