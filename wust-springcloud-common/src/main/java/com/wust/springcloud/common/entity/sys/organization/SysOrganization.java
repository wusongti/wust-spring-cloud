package com.wust.springcloud.common.entity.sys.organization;

import com.wust.springcloud.common.entity.BaseEntity;


/**
 * Created by WST on 2019/6/3.
 */
public class SysOrganization extends BaseEntity{
    private static final long serialVersionUID = 1638234693920231189L;

    private Long pid;
    private String type;
    private Long relationId;


    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getRelationId() {
        return relationId;
    }

    public void setRelationId(Long relationId) {
        this.relationId = relationId;
    }
}
