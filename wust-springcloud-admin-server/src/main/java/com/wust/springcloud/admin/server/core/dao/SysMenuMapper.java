package com.wust.springcloud.admin.server.core.dao;

import com.wust.springcloud.common.dao.IBaseMapper;
import com.wust.springcloud.common.dto.MenuTreeDto;
import com.wust.springcloud.common.entity.sys.menu.SysMenu;
import com.wust.springcloud.common.entity.sys.menu.SysMenuList;
import com.wust.springcloud.common.entity.sys.menu.SysMenuSearch;
import org.springframework.dao.DataAccessException;
import java.util.List;


public interface SysMenuMapper  extends IBaseMapper<SysMenu> {
	List<SysMenuList> listPage(SysMenuSearch search) throws DataAccessException;

	List<SysMenuList> findByCondition(SysMenuSearch search) throws DataAccessException;

	/**
	 * 根据组织id（也就是角色对应的组织id）获取菜单集合
	 * @param organizationId
	 * @return
	 */
	List<MenuTreeDto> findByOrganizationId(String organizationId);


	/**
	 * 删除所有
	 * @return
	 * @throws DataAccessException
	 */
	int deleteAll() throws DataAccessException;
}
