package com.frankfang.aspect;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@Order(Ordered.HIGHEST_PRECEDENCE)
public @interface RequestLimit {

    /**
     *
     * 允许访问的次数，默认值为1
     */
    int count() default 1;

    /**
     *
     * 时间段，单位为毫秒，默认值一秒钟
     */
    long time() default 1000;

}
