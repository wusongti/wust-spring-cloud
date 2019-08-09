package com.wust.springcloud.common.entity.sys.company;


import com.wust.springcloud.common.entity.BaseEntity;


/**
 * Created by WST on 2019/6/3.
 */
public class SysCompany extends BaseEntity {
    private static final long serialVersionUID = -3098131026241415716L;

    private String code;
    private String name;	        // 名称
    private String leader;		    // 公司负责人
    private String description;     // 描述
    private String type;            // 类型：代理商、总公司、分公司


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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return super.toString() + "\nSysCompany{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", leader='" + leader + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
