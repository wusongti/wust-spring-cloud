package com.wust.springcloud.common.entity.sys.organization;

import com.wust.springcloud.common.entity.BaseEntity;

/**
 * Created by WST on 2019/6/3.
 */
public class SysOrganization extends BaseEntity{
    private static final long serialVersionUID = 1638234693920231189L;

    private String id;
    private String pid;
    private String type;
    private String relationId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }
}
