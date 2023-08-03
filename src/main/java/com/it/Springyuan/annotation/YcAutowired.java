package com.it.Springyuan.annotation;

import java.io.EOFException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.EventListener;

@Target({ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.PARAMETER,ElementType.FIELD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface YcAutowired {
    boolean required() default true;

}
