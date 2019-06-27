package com.wust.springcloud.sso.server.service.impl;

import com.wust.springcloud.common.entity.sys.operationlog.SysOperationLog;
import com.wust.springcloud.sso.server.dao.SysOperationLogMapper;
import com.wust.springcloud.sso.server.service.SysOperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * Created by WST on 2019/5/28.
 */
@Service("sysOperationLogServiceImpl")
public class SysOperationLogServiceImpl implements SysOperationLogService {
    @Autowired
    private SysOperationLogMapper sysOperationLogMapper;


    @Transactional(rollbackFor=Exception.class)
    @Override
    public int batchInsert(List<SysOperationLog> sysOperationLogs) {
        return sysOperationLogMapper.batchInsert(sysOperationLogs);
    }
}
