package com.wust.springcloud.admin.server.core.dao;


import com.wust.springcloud.common.entity.sys.role.SysRole;
import com.wust.springcloud.common.entity.sys.role.SysRoleList;
import com.wust.springcloud.common.entity.sys.role.SysRoleSearch;
import org.springframework.dao.DataAccessException;

import java.util.List;


public interface SysRoleMapper {

    List<SysRoleList> listPage(SysRoleSearch search) throws DataAccessException;
    
    List<SysRoleList> findByCondition(SysRoleSearch search) throws DataAccessException;

	int batchInsert(List<SysRole> entities) throws DataAccessException;

	int batchUpdate(List<SysRole> entities) throws DataAccessException;
	
	int batchDelete(List<String> keys) throws DataAccessException;
}
