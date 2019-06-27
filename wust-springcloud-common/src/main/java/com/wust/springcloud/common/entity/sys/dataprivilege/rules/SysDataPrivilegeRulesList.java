package com.wust.springcloud.common.entity.sys.dataprivilege.rules;

/**
 * Created by WST on 2019/6/10.
 */
public class SysDataPrivilegeRulesList extends SysDataPrivilegeRules{
    private static final long serialVersionUID = -7736625563127523094L;

    private String businessName;

    private String typeName;

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
