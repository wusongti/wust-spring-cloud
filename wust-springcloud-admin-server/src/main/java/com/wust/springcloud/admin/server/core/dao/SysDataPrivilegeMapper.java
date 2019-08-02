package com.wust.springcloud.admin.server.core.dao;


import com.wust.springcloud.common.dao.IBaseMapper;
import com.wust.springcloud.common.entity.sys.dataprivilege.SysDataPrivilege;
import org.springframework.dao.DataAccessException;

/**
 * Created by WST on 2019/6/10.
 */
public interface SysDataPrivilegeMapper extends IBaseMapper<SysDataPrivilege> {
    int deleteAll() throws DataAccessException;
}
