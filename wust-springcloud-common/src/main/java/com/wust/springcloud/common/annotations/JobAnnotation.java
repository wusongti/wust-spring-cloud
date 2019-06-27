package com.wust.springcloud.common.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * j标识为Job的注解
 * Created by WST on 2019/6/14.
 @Retention(RetentionPolicy.RUNTIME)
 @Target({ElementType.METHOD})
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface JobAnnotation {
    String jobName();
    String jobDescription();
    String jobGroupName();
    String cronExpression();
}
