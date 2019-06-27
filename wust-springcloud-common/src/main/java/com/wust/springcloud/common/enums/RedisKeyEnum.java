package com.wust.springcloud.common.enums;

/**
 * Created by WST on 2019/4/29.
 */
public enum  RedisKeyEnum {

    REDIS_TABLE_KEY_GROUP_LOOKUP_CODE_BY_ROOT_CODE_AND_NAME("%s_REDIS_TABLE_KEY_GROUP_LOOKUP_CODE_BY_ROOT_CODE_AND_NAME"),
    REDIS_TABLE_KEY_GROUP_LOOKUP_NAME_BY_CODE("%s_REDIS_TABLE_KEY_GROUP_LOOKUP_NAME_BY_CODE"),
    REDIS_TABLE_KEY_GROUP_GROUP_LOOKUP_BY_PID("%s_REDIS_TABLE_KEY_GROUP_GROUP_LOOKUP_BY_PID"),
    REDIS_TABLE_KEY_DATA_PRIVILEGE_ANNOTATIONS("%s_a5362b48-8cbb-11e9-a68d-0050568e63cd"),
    REDIS_TABLE_KEY_DATA_SOURCE("REDIS_TABLE_KEY_DATA_SOURCE");

    RedisKeyEnum(String stringValue){
        this.stringValue = stringValue;
    }

    private String stringValue;
    public String getStringValue() {
        return stringValue;
    }
}
