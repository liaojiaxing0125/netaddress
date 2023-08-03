package com.it.aop;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan("com.it.aop")
@EnableAspectJAutoProxy //表示启动,AspectJ支持
public class Config {
}
