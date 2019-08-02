package com.wust.springcloud.common.entity.sys.dataprivilege.rules;

import com.wust.springcloud.common.entity.BaseEntity;

/**
 * Created by WST on 2019/6/10.
 */
public class SysDataPrivilegeRules extends BaseEntity{
    private static final long serialVersionUID = 3076982881361832373L;

    private Long dataPrivilegeId;
    private String dataPrivilegeUuid;
    private String type;
    private String expression;

    public Long getDataPrivilegeId() {
        return dataPrivilegeId;
    }

    public void setDataPrivilegeId(Long dataPrivilegeId) {
        this.dataPrivilegeId = dataPrivilegeId;
    }

    public String getDataPrivilegeUuid() {
        return dataPrivilegeUuid;
    }

    public void setDataPrivilegeUuid(String dataPrivilegeUuid) {
        this.dataPrivilegeUuid = dataPrivilegeUuid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    @Override
    public String toString() {
        return super.toString() + "\nSysDataPrivilegeRules{" +
                "dataPrivilegeId=" + dataPrivilegeId +
                ", dataPrivilegeUuid='" + dataPrivilegeUuid + '\'' +
                ", type='" + type + '\'' +
                ", expression='" + expression + '\'' +
                '}';
    }
}
