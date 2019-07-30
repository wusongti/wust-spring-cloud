package com.wust.springcloud.common.entity.sys.project;

import com.wust.springcloud.common.entity.BaseEntity;

/**
 * @author ：wust
 * @date ：Created in 2019/7/30 11:39
 * @description：
 * @version:
 */
public class SysProject extends BaseEntity {
    private static final long serialVersionUID = 869596893803958296L;

    private String id;
    private String code;
    private String name;
    private String addr;
    private String description;

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

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return super.toString() + "\nSysProject{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", addr='" + addr + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
