package com.wust.springcloud.common.entity.sys.department;

import com.wust.springcloud.common.entity.BaseEntity;


/**
 * Created by WST on 2019/6/3.
 */
public class SysDepartment extends BaseEntity{
    private static final long serialVersionUID = -2583497779223895688L;

    private String code;
    private String name;	        // 名称
    private String leader;		    // 公司负责人
    private String description;     // 描述


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
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", leader='" + leader + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
