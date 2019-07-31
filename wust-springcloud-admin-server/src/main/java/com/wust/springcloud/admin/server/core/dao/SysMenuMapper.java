package com.wust.springcloud.admin.server.core.dao;

import com.wust.springcloud.common.dao.BaseMapper;
import com.wust.springcloud.common.dto.MenuTreeDto;
import org.springframework.dao.DataAccessException;
import java.util.List;


public interface SysMenuMapper  extends BaseMapper {
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
