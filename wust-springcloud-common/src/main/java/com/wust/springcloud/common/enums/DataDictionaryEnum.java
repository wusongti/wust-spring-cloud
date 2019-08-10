package com.wust.springcloud.common.enums;

/**
 * 经常使用的数据字典，使用枚举来避免程序中的硬编码
 */
public enum DataDictionaryEnum {

    ORGANIZATION_TYPE_AGENT("101101"),   // 组织架构类别：代理商
    ORGANIZATION_TYPE_PARENT_COMPANY ("101104"),   // 组织架构类别：总公司
    ORGANIZATION_TYPE_BRANCH_COMPANY("101107"),   // 组织架构类别：分公司
    ORGANIZATION_TYPE_PROJECT("101109"),   // 组织架构类别：项目
    ORGANIZATION_TYPE_DEPARTMENT("101111"),   // 组织架构类别：部门
    ORGANIZATION_TYPE_ROLE("101113"),   // 组织架构类别：角色
    ORGANIZATION_TYPE_USER("101115"),   // 组织架构类别：用户

    USER_TYPE_PLATFORM_ADMIN("100401"), // 用户类型：平台超级管理员
    USER_TYPE_PLATFORM_USER("100402"), // 用户类型：平台普通管理员
    USER_TYPE_BUSINESS_ADMIN("100403"), // 用户类型：运营方管理账号
    USER_TYPE_BUSINESS_USER("100404"); // 用户类型： 业务员账号



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
