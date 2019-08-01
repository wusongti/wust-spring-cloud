package com.wust.springcloud.admin.server.core.service.defaults.impl;

import com.wust.springcloud.admin.server.core.dao.SysOperationLogMapper;
import com.wust.springcloud.admin.server.core.service.defaults.SysOperationLogService;
import com.wust.springcloud.common.dao.IBaseMapper;
import com.wust.springcloud.common.service.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by WST on 2019/5/28.
 */
@Service("sysOperationLogServiceImpl")
public class SysOperationLogServiceImpl extends BaseServiceImpl implements SysOperationLogService {
    @Autowired
    private SysOperationLogMapper sysOperationLogMapper;

    @Override
    protected IBaseMapper getBaseMapper() {
        return sysOperationLogMapper;
    }

}
