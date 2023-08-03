package com.it.Springyuan.annotation;

import com.sun.corba.se.spi.ior.IdentifiableFactory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.PARAMETER,ElementType.FIELD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface YcQualifier {
    String value();
}
