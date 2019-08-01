package com.wust.springcloud.admin.server.core.dao;

import com.wust.springcloud.common.dao.IBaseMapper;
import com.wust.springcloud.common.entity.sys.role.resource.SysRoleResource;
import com.wust.springcloud.common.entity.sys.role.resource.SysRoleResourceList;
import org.springframework.dao.DataAccessException;
import java.util.List;



public interface SysRoleResourceMapper   extends IBaseMapper<SysRoleResource> {

	List<SysRoleResourceList> groupByOrganizationId(String type) throws DataAccessException;

    int deleteDirtyMenu()throws DataAccessException;

	int deleteDirtyResource()throws DataAccessException;

	int batchDeleteByOrganizationIds(List<String> organizationIds) throws DataAccessException;
}
