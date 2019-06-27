package com.wust.springcloud.sso.server.dao;

import com.wust.springcloud.common.entity.sys.operationlog.SysOperationLog;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * Created by WST on 2019/5/28.
 */
public interface SysOperationLogMapper {
    int batchInsert(List<SysOperationLog> sysOperationLogs) throws DataAccessException;
}
