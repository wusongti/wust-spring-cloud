package com.wust.springcloud.common.entity.sys.role;

import com.wust.springcloud.common.entity.BaseEntity;

/**
 * Created by WST on 2019/4/28.
 */
public class SysRole extends BaseEntity {
    private static final long serialVersionUID = 9169618756448096826L;
    private String id;		        // 角色ID
    private String code;
    private String name;	        // 角色名称
    private String description;     // 角色描述
    private String status;		    // 启用状态

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return super.toString() + "\nSysRole{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
