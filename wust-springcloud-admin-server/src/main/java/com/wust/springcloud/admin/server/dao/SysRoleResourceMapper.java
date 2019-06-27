package com.wust.springcloud.admin.server.dao;

import com.wust.springcloud.common.entity.sys.role.resource.SysRoleResource;
import com.wust.springcloud.common.entity.sys.role.resource.SysRoleResourceList;
import org.springframework.dao.DataAccessException;
import java.util.List;



public interface SysRoleResourceMapper {
	List<SysRoleResourceList> findByCondition(SysRoleResource sysRoleResource) throws DataAccessException;

	List<SysRoleResourceList> groupByOrganizationId(String type) throws DataAccessException;

    int batchInsert(List<SysRoleResource> list) throws DataAccessException;

    int batchDelete(List<String> list) throws DataAccessException;

    int deleteDirtyMenu()throws DataAccessException;

	int deleteDirtyResource()throws DataAccessException;

	int batchDeleteByOrganizationIds(List<String> organizationIds) throws DataAccessException;
}
