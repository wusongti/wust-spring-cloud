package com.wust.springcloud.admin.server.dao;


import com.wust.springcloud.common.dto.ResourceTreeDto;
import com.wust.springcloud.common.entity.sys.resource.SysResource;
import com.wust.springcloud.common.entity.sys.resource.SysResourceSearch;
import org.springframework.dao.DataAccessException;
import java.util.List;
import java.util.Map;

public interface SysResourceMapper {
	List<SysResource> findByCondition(SysResourceSearch search) throws DataAccessException;

	List<SysResource> findAnonResourcesByMenuId(String menuId) throws DataAccessException;

	List<ResourceTreeDto> findByOrganizationId(String organizationId);

	int batchInsert(List<SysResource> entities) throws DataAccessException;

	int deleteAll() throws DataAccessException;
}
