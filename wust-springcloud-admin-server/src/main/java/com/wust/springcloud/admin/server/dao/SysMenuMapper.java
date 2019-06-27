package com.wust.springcloud.admin.server.dao;

import com.wust.springcloud.common.dto.MenuTreeDto;
import com.wust.springcloud.common.entity.sys.menu.SysMenu;
import org.springframework.dao.DataAccessException;
import java.util.List;


public interface SysMenuMapper {
	/**
	 * 根据组织id（也就是角色对应的组织id）获取菜单集合
	 * @param organizationId
	 * @return
	 */
	List<MenuTreeDto> findByOrganizationId(String organizationId);

	/**
	 * 批量新增
	 * @param entities
	 * @return
	 * @throws DataAccessException
	 */
	int batchInsert(List<SysMenu> entities) throws DataAccessException;

	/**
	 * 删除所有
	 * @return
	 * @throws DataAccessException
	 */
	int deleteAll() throws DataAccessException;
}
