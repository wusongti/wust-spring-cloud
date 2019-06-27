package com.wust.springcloud.common.entity.sys.dataprivilege.rules;

import com.wust.springcloud.common.entity.BaseEntity;

/**
 * Created by WST on 2019/6/10.
 */
public class SysDataPrivilegeRules extends BaseEntity{
    private static final long serialVersionUID = 3076982881361832373L;

    private String id;
    private String dataPrivilegeId;
    private String type;
    private String expression;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDataPrivilegeId() {
        return dataPrivilegeId;
    }

    public void setDataPrivilegeId(String dataPrivilegeId) {
        this.dataPrivilegeId = dataPrivilegeId;
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


}
