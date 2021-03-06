package com.wust.springcloud.admin.server.core.dao;

import com.wust.springcloud.common.dao.IBaseMapper;
import com.wust.springcloud.common.entity.sys.operationlog.SysOperationLog;
import com.wust.springcloud.common.entity.sys.operationlog.SysOperationLogList;
import com.wust.springcloud.common.entity.sys.operationlog.SysOperationLogSearch;
import org.springframework.dao.DataAccessException;
import java.util.List;

/**
 * Created by WST on 2019/5/28.
 */
public interface SysOperationLogMapper  extends IBaseMapper<SysOperationLog> {
    List<SysOperationLogList> listPage(SysOperationLogSearch search) throws DataAccessException;
}
