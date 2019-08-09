package com.wust.springcloud.admin.server.core.dao;


import com.wust.springcloud.common.dao.IBaseMapper;
import com.wust.springcloud.common.entity.sys.role.SysRole;
import com.wust.springcloud.common.entity.sys.role.SysRoleList;
import com.wust.springcloud.common.entity.sys.role.SysRoleSearch;
import org.springframework.dao.DataAccessException;
import java.util.List;


public interface SysRoleMapper   extends IBaseMapper<SysRole> {

    List<SysRoleList> listPage(SysRoleSearch search) throws DataAccessException;
}
