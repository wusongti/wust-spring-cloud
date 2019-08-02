package com.wust.springcloud.sso.server.core.service.impl;

import com.wust.springcloud.common.dao.IBaseMapper;
import com.wust.springcloud.common.service.BaseServiceImpl;
import com.wust.springcloud.sso.server.core.service.SysOperationLogService;
import com.wust.springcloud.sso.server.core.dao.SysOperationLogMapper;
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
