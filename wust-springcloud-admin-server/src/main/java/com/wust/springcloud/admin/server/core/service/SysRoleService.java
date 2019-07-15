package com.wust.springcloud.admin.server.core.service;

import com.wust.springcloud.common.dto.ResponseDto;
import com.wust.springcloud.common.entity.sys.role.SysRole;
import com.wust.springcloud.common.entity.sys.role.SysRoleList;
import com.wust.springcloud.common.entity.sys.role.SysRoleSearch;

import java.util.List;

/**
 * Created by WST on 2019/5/27.
 */
public interface SysRoleService {
    List<SysRoleList> listPage(SysRoleSearch search);

    List<SysRoleList> findByCondition(SysRoleSearch search);

    ResponseDto findFunctionTreeByOrganizationId(String organizationId);

    int insert(SysRole entity);

    int update(SysRole entity);

    int batchDelete(List<String> ids);
}
