package com.wust.springcloud.common.enums;

/**
 * 数据字典对应的枚举，避免在程序中硬编码，所以使用此枚举代替
 */
public enum DataDictionaryEnum {

    ORGANIZATION_TYPE_AGENT("101101"),   // 组织架构类别：代理商
    ORGANIZATION_TYPE_PARENT_COMPANY ("101104"),   // 组织架构类别：总公司
    ORGANIZATION_TYPE_BRANCH_COMPANY("101107"),   // 组织架构类别：分公司
    ORGANIZATION_TYPE_PROJECT("101109"),   // 组织架构类别：项目
    ORGANIZATION_TYPE_DEPARTMENT("101111"),   // 组织架构类别：部门
    ORGANIZATION_TYPE_ROLE("101113"),   // 组织架构类别：角色
    ORGANIZATION_TYPE_USER("101115");   // 组织架构类别：用户

    private String stringValue;

    public String getStringValue() {
        return stringValue;
    }

    DataDictionaryEnum() {
    }

    DataDictionaryEnum(String stringValue) {
        this.stringValue = stringValue;
    }
}
