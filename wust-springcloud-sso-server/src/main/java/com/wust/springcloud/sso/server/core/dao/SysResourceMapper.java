package com.wust.springcloud.sso.server.core.dao;


import com.wust.springcloud.common.entity.sys.resource.SysResource;
import com.wust.springcloud.common.entity.sys.resource.SysResourceSearch;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Map;

public interface SysResourceMapper {
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
	List<SysResource> findAnonResourcesByUserId(String userId) throws DataAccessException;


	/**
	 * 非超级管理员：获取白名单资源权限
	 * @param useId
	 * @return
	 */
	List<SysResource> findResourcesByUserId(String useId) throws DataAccessException;

	/**
	 * 根据菜单获取其下面的资源集合
	 * @param menuId
	 * @return
	 */
	List<SysResource> findAnonResourcesByMenuId(String menuId) throws DataAccessException;

	/**
	 * 获取公共白名单集合
	 * @return
	 */
	List<SysResource> findCommonAnonResources() throws DataAccessException;
}
