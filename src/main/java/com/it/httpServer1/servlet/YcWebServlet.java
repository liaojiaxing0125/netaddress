package com.it.httpServer1.servlet;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})//未来只能加载到类 注解在表示作用范围
@Retention(RetentionPolicy.RUNTIME)//Runtime表示这个注解运行时还有
public @interface YcWebServlet {
    String value() default ""; //default默认值为空
}
//使用1:YcWebServlet(value="/hello")
//使用二:YcWebServlet("/hello")
