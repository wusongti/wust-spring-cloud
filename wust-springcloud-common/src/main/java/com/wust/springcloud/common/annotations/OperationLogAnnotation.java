package com.wust.springcloud.common.annotations;

import com.wust.springcloud.common.enums.OperationLogEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 操作日志标识
 * Created by WST on 2019/5/27.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface OperationLogAnnotation {
    // 模块名
    OperationLogEnum moduleName();

    // 业务名称
    String businessName();

    // 操作类型
    OperationLogEnum operationType();
}
