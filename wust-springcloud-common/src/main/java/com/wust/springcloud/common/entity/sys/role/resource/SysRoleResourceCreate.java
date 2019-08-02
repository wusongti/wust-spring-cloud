package com.wust.springcloud.common.entity.sys.role.resource;

import java.util.List;

/**
 * Created by WST on 2019/6/6.
 */
public class SysRoleResourceCreate {
    private static final long serialVersionUID = 2179169002800939853L;

    private Long pid;
    private Long roleId;
    private Long organizationId;
    private List<SysRoleResource> sysRoleResources;

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public List<SysRoleResource> getSysRoleResources() {
        return sysRoleResources;
    }

    public void setSysRoleResources(List<SysRoleResource> sysRoleResources) {
        this.sysRoleResources = sysRoleResources;
    }
}
