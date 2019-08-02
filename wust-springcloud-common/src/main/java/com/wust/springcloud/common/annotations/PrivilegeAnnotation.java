package com.wust.springcloud.common.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据权限标识
 * Created by WST on 2019/6/10.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface PrivilegeAnnotation {
    String uuid();

    // 业务名称
    String businessName();
}
