package com.wust.springcloud.admin.server.core.service;

import com.wust.springcloud.common.entity.sys.operationlog.SysOperationLog;
import com.wust.springcloud.common.entity.sys.operationlog.SysOperationLogList;
import com.wust.springcloud.common.entity.sys.operationlog.SysOperationLogSearch;
import java.util.List;

/**
 * Created by WST on 2019/5/28.
 */
public interface SysOperationLogService {
    List<SysOperationLogList> listPage(SysOperationLogSearch search);

    int batchInsert(List<SysOperationLog> entities);
}
