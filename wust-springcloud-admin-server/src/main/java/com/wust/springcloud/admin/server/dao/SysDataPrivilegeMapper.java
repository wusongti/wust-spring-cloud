package com.wust.springcloud.admin.server.dao;


import com.wust.springcloud.common.entity.sys.dataprivilege.SysDataPrivilege;
import com.wust.springcloud.common.entity.sys.dataprivilege.SysDataPrivilegeList;
import com.wust.springcloud.common.entity.sys.dataprivilege.SysDataPrivilegeSearch;
import org.springframework.dao.DataAccessException;
import java.util.List;

/**
 * Created by WST on 2019/6/10.
 */
public interface SysDataPrivilegeMapper {
    List<SysDataPrivilegeList> listPage(SysDataPrivilegeSearch search) throws DataAccessException;

    List<SysDataPrivilegeList> findByCondition(SysDataPrivilegeSearch search) throws DataAccessException;

    int batchInsert(List<SysDataPrivilege> entities) throws DataAccessException;

    int deleteAll() throws DataAccessException;

    int batchDelete(List<String> keys) throws DataAccessException;
}
