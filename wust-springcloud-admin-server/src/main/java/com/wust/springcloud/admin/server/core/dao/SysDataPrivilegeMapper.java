package com.wust.springcloud.admin.server.core.dao;


import com.wust.springcloud.common.dao.IBaseMapper;
import com.wust.springcloud.common.entity.sys.dataprivilege.SysDataPrivilege;
import com.wust.springcloud.common.entity.sys.dataprivilege.SysDataPrivilegeList;
import com.wust.springcloud.common.entity.sys.dataprivilege.SysDataPrivilegeSearch;
import org.springframework.dao.DataAccessException;
import java.util.List;

/**
 * Created by WST on 2019/6/10.
 */
public interface SysDataPrivilegeMapper extends IBaseMapper<SysDataPrivilege> {
    List<SysDataPrivilegeList> listPage(SysDataPrivilegeSearch search) throws DataAccessException;

    List<SysDataPrivilegeList> findByCondition(SysDataPrivilegeSearch search) throws DataAccessException;

    int deleteAll() throws DataAccessException;
}
