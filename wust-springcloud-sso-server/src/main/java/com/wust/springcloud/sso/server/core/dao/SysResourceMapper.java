package com.wust.springcloud.sso.server.core.dao;


import com.wust.springcloud.common.dao.IBaseMapper;
import com.wust.springcloud.common.entity.sys.resource.SysResource;
import org.springframework.dao.DataAccessException;
import java.util.List;

public interface SysResourceMapper extends IBaseMapper<SysResource> {
	/**
	 * 超级管理员：获取非白名单资源权限
	 * @return
	 */
	List<SysResource> findAllResources4systemAdmin() throws DataAccessException;


	/**
	 * 超级管理员：获取白名单资源权限
	 * @return
	 */
	List<SysResource> findAllAnonResources4systemAdmin() throws DataAccessException;


	/**
	 * 非超级管理员：获取非白名单资源权限
	 * @param userId
	 * @return
	 */
	List<SysResource> findAnonResourcesByUserId(Long userId) throws DataAccessException;


	/**
	 * 非超级管理员：获取白名单资源权限
	 * @param useId
	 * @return
	 */
	List<SysResource> findResourcesByUserId(Long useId) throws DataAccessException;
}
