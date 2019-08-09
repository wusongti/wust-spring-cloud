package com.wust.springcloud.common.entity.sys.userorganization;

import com.wust.springcloud.common.entity.BaseEntity;

import javax.persistence.*;
import java.util.Date;


public class SysUserOrganization extends BaseEntity {

    private static final long serialVersionUID = -4178606011060144007L;
    private Long userId;

    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "department_id")
    private Long departmentId;

    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "branch_company_id")
    private Long branchCompanyId;

    @Column(name = "parent_company_id")
    private Long parentCompanyId;

    @Column(name = "agent_id")
    private Long agentId;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getBranchCompanyId() {
        return branchCompanyId;
    }

    public void setBranchCompanyId(Long branchCompanyId) {
        this.branchCompanyId = branchCompanyId;
    }

    public Long getParentCompanyId() {
        return parentCompanyId;
    }

    public void setParentCompanyId(Long parentCompanyId) {
        this.parentCompanyId = parentCompanyId;
    }

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }
}