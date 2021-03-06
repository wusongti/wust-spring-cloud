package com.wust.springcloud.common.entity.sys.role.resource;

import com.wust.springcloud.common.entity.BaseEntity;

/**
 * Created by WST on 2019/4/28.
 */
public class SysRoleResource extends BaseEntity {
    private static final long serialVersionUID = 9169618756448096826L;

    private Long organizationId;		// 组织id
    private String resourceId;	        //资源id, 当srcType为m时,为菜单id,为r时,指向资源id
    private String type;		        //资源类型,m为菜单,r为资源


    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return super.toString() + "\nSysRoleResource{" +
                ", organizationId='" + organizationId + '\'' +
                ", resourceId='" + resourceId + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
