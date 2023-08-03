package com.it.Springyuan.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
//target定义类的作用范围
@Target(ElementType.TYPE)
//表示在哪一个阶段一个是源代码 编译 运行
@Retention(RetentionPolicy.RUNTIME)
public @interface YcConfiguration {
}
