package com.wust.springcloud.admin.server.core.service;

import com.wust.springcloud.common.dto.ResponseDto;
import com.wust.springcloud.common.entity.sys.role.resource.SysRoleResourceCreate;
import com.wust.springcloud.common.service.BaseService;

/**
 * Created by WST on 2019/6/3.
 */
public interface SysOrganizationService extends BaseService {

    /**
     * 为角色设置功能权限
     * @param sysRoleResourceCreate
     * @return
     */
    ResponseDto setFunctionPermissions(SysRoleResourceCreate sysRoleResourceCreate);
}
