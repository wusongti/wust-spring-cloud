package com.wust.springcloud.common.entity.sys.department;

import com.wust.springcloud.common.entity.BaseEntity;

/**
 * Created by WST on 2019/6/3.
 */
public class SysDepartment extends BaseEntity{
    private static final long serialVersionUID = -2583497779223895688L;

    private String id;
    private String code;
    private String pcode;		    // 父级部门编码
    private String name;	        // 名称
    private String leader;		    // 公司负责人
    private String description;     // 描述

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

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return super.toString() + "\nSysDepartment{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", pcode='" + pcode + '\'' +
                ", name='" + name + '\'' +
                ", leader='" + leader + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
