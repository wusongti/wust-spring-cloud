package com.wust.springcloud.admin.server.core.dao;


import com.wust.springcloud.common.dao.BaseMapper;
import com.wust.springcloud.common.dto.ResourceTreeDto;
import com.wust.springcloud.common.entity.sys.resource.SysResource;
import org.springframework.dao.DataAccessException;
import java.util.List;

public interface SysResourceMapper extends BaseMapper {

	List<SysResource> findAnonResourcesByMenuId(String menuId) throws DataAccessException;

	List<ResourceTreeDto> findByOrganizationId(String organizationId);

	int deleteAll() throws DataAccessException;
}
