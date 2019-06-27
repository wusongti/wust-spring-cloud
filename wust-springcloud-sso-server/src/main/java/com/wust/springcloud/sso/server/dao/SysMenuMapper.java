package com.wust.springcloud.sso.server.dao;

import com.wust.springcloud.common.entity.sys.menu.SysMenu;
import com.wust.springcloud.common.entity.sys.menu.SysMenuSearch;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Map;


public interface SysMenuMapper {
	List<SysMenu> findByCondition(SysMenuSearch sysMenuSearch) throws DataAccessException;

	/**
	 * 超级管理员查询
	 * @return
	 */
	List<SysMenu> findAllMenus4SystemAdmin() throws DataAccessException;


	/**
	 * 非超级管理员查询
	 * @param loginName
	 * @return
	 */
	List<SysMenu> findMenuByUserId(String loginName) throws DataAccessException;
}
