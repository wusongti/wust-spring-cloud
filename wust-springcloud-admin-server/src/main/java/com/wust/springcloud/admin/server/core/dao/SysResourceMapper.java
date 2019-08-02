package com.wust.springcloud.admin.server.core.dao;


import com.wust.springcloud.common.dao.IBaseMapper;
import com.wust.springcloud.common.dto.ResourceTreeDto;
import com.wust.springcloud.common.entity.sys.resource.SysResource;
import com.wust.springcloud.common.entity.sys.resource.SysResourceList;
import com.wust.springcloud.common.entity.sys.resource.SysResourceSearch;
import org.springframework.dao.DataAccessException;
import java.util.List;

public interface SysResourceMapper extends IBaseMapper<SysResource> {
	List<SysResourceList> listPage(SysResourceSearch search) throws DataAccessException;

	List<SysResourceList> findByCondition(SysResourceSearch search) throws DataAccessException;

	List<SysResource> findAnonResourcesByMenuId(String menuCode) throws DataAccessException;

	List<ResourceTreeDto> findByOrganizationId(Long organizationId);

	int deleteAll() throws DataAccessException;
}
