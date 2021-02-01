package com.fzcoder.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Record {

    /**
     * 记录类型
     * @return 记录的类型
     */
    RecordType type();


    /**
     * 数据库操作类型
     * @return 数据库操作的类型
     */
    RecordOperationType operationType();

}
