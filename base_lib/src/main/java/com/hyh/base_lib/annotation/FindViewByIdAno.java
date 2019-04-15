package com.hyh.base_lib.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FindViewByIdAno {
    /**
     * 保存视图控件的id
     * @return
     */
    int value();
}
