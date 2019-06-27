package com.wust.springcloud.common.entity.sys.role.resource;

import java.util.List;

/**
 * Created by WST on 2019/6/6.
 */
public class SysRoleResourceCreate {
    private static final long serialVersionUID = 2179169002800939853L;

    private String pId;
    private String roleId;
    private String organizationId;
    private List<SysRoleResource> sysRoleResources;

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public List<SysRoleResource> getSysRoleResources() {
        return sysRoleResources;
    }

    public void setSysRoleResources(List<SysRoleResource> sysRoleResources) {
        this.sysRoleResources = sysRoleResources;
    }
}
