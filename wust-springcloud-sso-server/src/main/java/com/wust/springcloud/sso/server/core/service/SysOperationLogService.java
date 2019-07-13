package com.wust.springcloud.sso.server.core.service;

import com.wust.springcloud.common.entity.sys.operationlog.SysOperationLog;
import java.util.List;

/**
 * Created by WST on 2019/5/28.
 */
public interface SysOperationLogService {
    int batchInsert(List<SysOperationLog> sysOperationLogs);
}
