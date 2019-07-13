package com.wust.springcloud.admin.server.core.dao;

import com.wust.springcloud.common.annotations.PrivilegeAnnotation;
import com.wust.springcloud.common.entity.sys.operationlog.SysOperationLog;
import com.wust.springcloud.common.entity.sys.operationlog.SysOperationLogList;
import com.wust.springcloud.common.entity.sys.operationlog.SysOperationLogSearch;
import org.springframework.dao.DataAccessException;
import java.util.List;

/**
 * Created by WST on 2019/5/28.
 */
public interface SysOperationLogMapper {
    @PrivilegeAnnotation(id = "ae5c122c-8cb5-11e9-a68d-0050568e63cd",businessName = "操作日志")
    List<SysOperationLogList> listPage(SysOperationLogSearch search) throws DataAccessException;

    int batchInsert(List<SysOperationLog> entities) throws DataAccessException;
}
