package com.wust.springcloud.sso.server.core.dao;

import com.wust.springcloud.common.dao.IBaseMapper;
import com.wust.springcloud.common.entity.sys.menu.SysMenu;
import com.wust.springcloud.common.entity.sys.menu.SysMenuSearch;
import org.springframework.dao.DataAccessException;
import java.util.List;


public interface SysMenuMapper extends IBaseMapper<SysMenu> {
	List<SysMenu> findByCondition(SysMenuSearch sysMenuSearch) throws DataAccessException;

	/**
	 * 超级管理员查询
	 * @return
	 */
	List<SysMenu> findAllMenus4SystemAdmin() throws DataAccessException;


	/**
	 * 非超级管理员查询
	 * @param userId
	 * @return
	 */
	List<SysMenu> findMenuByUserId(Long userId) throws DataAccessException;
}
