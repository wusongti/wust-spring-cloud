package com.wust.springcloud.common.enums;

/**
 * 应用枚举
 * Created by WST on 2019/3/29.
 */
public enum  ApplicationEnum {

    JWT_ACCESS_SECRET("2f0dd6a1-46d3-11e9-91bd-0050568e00c5"),

    RC4_TOKEN_KEY("16d7c967-6177-11e9-a68d-0050568e63cd"),

    WEB_LOGIN_KEY("WEB_LOGIN_KEY_%s"),

    X_AUTH_TOKEN("x-auth-token"),

    X_AUTH_TOKEN_EXPIRE_TIME(10), // 单位：分钟

    RC4_LOGIN_PASSWORD("jZ5$x!6yeAo1Qe^r89"),

    MENUT_TYPE_M("m"),              // 资源类型，菜单

    MENUT_TYPE_R("r"),              // 资源类型，按钮

    DEFAULT;

    ApplicationEnum(){}

    ApplicationEnum(String stringValue){
        this.stringValue = stringValue;
    }

    ApplicationEnum(int intValue){
        this.intValue = intValue;
    }

    ApplicationEnum(long longValue){
        this.longValue = longValue;
    }

    private String stringValue;
    public String getStringValue() {
        return stringValue;
    }

    private int intValue;
    public int getIntValue() {
        return intValue;
    }

    private long longValue;
    public long getLongValue() {
        return longValue;
    }
}
