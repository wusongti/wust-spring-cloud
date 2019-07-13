package com.wust.springcloud.admin.server.core.service;

import com.wust.springcloud.common.dto.MessageMap;
import com.wust.springcloud.common.entity.sys.organization.SysOrganization;
import com.wust.springcloud.common.entity.sys.organization.SysOrganizationList;
import com.wust.springcloud.common.entity.sys.organization.SysOrganizationSearch;
import com.wust.springcloud.common.entity.sys.role.resource.SysRoleResourceCreate;

import java.util.List;

/**
 * Created by WST on 2019/6/3.
 */
public interface SysOrganizationService {
    List<SysOrganizationList> listPage(SysOrganizationSearch search);

    List<SysOrganizationList> findByCondition(SysOrganizationSearch search);

    int batchInsert(List<SysOrganization> entities);

    int batchDelete(List<String> keys);

    /**
     * 为角色设置功能权限
     * @param sysRoleResourceCreate
     * @return
     */
    MessageMap setFunctionPermissions(SysRoleResourceCreate sysRoleResourceCreate);
}
