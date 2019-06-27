package com.wust.springcloud.common.entity.sys.company;

import com.wust.springcloud.common.entity.BaseEntity;

/**
 * Created by WST on 2019/6/3.
 */
public class SysCompany extends BaseEntity{
    private static final long serialVersionUID = -3098131026241415716L;

    private String id;
    private String code;
    private String pcode;		    // 父级公司编码
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
        return super.toString() + "\nSysCompany{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", pcode='" + pcode + '\'' +
                ", name='" + name + '\'' +
                ", leader='" + leader + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
