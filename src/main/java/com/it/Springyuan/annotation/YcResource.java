package com.it.Springyuan.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Type;

import static java.lang.annotation.ElementType.*;

@Target({TYPE,FIELD,METHOD,CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
public @interface YcResource {
    String name() default "";
}
