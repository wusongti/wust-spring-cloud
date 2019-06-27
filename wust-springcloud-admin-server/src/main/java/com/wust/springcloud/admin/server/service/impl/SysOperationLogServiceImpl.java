package com.wust.springcloud.admin.server.service.impl;

import com.wust.springcloud.admin.server.dao.SysOperationLogMapper;
import com.wust.springcloud.admin.server.service.SysOperationLogService;
import com.wust.springcloud.common.entity.sys.operationlog.SysOperationLog;
import com.wust.springcloud.common.entity.sys.operationlog.SysOperationLogList;
import com.wust.springcloud.common.entity.sys.operationlog.SysOperationLogSearch;
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

    @Override
    public List<SysOperationLogList> listPage(SysOperationLogSearch search) {
        return sysOperationLogMapper.listPage(search);
    }

    @Transactional(rollbackFor=Exception.class)
    @Override
    public int batchInsert(List<SysOperationLog> entities) {
        return sysOperationLogMapper.batchInsert(entities);
    }
}
