package com.wust.springcloud.admin.server.core.service.impl;

import com.wust.springcloud.admin.server.core.dao.SysProjectMapper;
import com.wust.springcloud.admin.server.core.service.SysProjectService;
import com.wust.springcloud.common.dao.IBaseMapper;
import com.wust.springcloud.common.service.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ：wust
 * @date ：Created in 2019/7/30 14:15
 * @description：
 * @version:
 */
@Service("sysProjectServiceImpl")
public class SysProjectServiceImpl extends BaseServiceImpl implements SysProjectService {

    @Autowired
    private SysProjectMapper sysProjectMapper;

    @Override
    protected IBaseMapper getBaseMapper() {
        return sysProjectMapper;
    }
}
