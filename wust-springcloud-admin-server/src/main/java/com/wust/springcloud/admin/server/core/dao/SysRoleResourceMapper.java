package com.wust.springcloud.admin.server.core.dao;

import com.wust.springcloud.common.dao.BaseMapper;
import com.wust.springcloud.common.entity.sys.role.resource.SysRoleResourceList;
import org.springframework.dao.DataAccessException;
import java.util.List;



public interface SysRoleResourceMapper   extends BaseMapper {

	List<SysRoleResourceList> groupByOrganizationId(String type) throws DataAccessException;

    int deleteDirtyMenu()throws DataAccessException;

	int deleteDirtyResource()throws DataAccessException;

	int batchDeleteByOrganizationIds(List<String> organizationIds) throws DataAccessException;
}
