package com.it.cglibproxy;


import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;

public class CglibProxyTool{
//        implements MethodInterceptor {
//    private Object target;
//
//    public CglibProxyTool(Object target) {
//        this.target = target;
//    }
//    public Object createProxy(){
//        Enhancer enhancer = new Enhancer();
//        enhancer.setSuperclass(this.target.getClass());//这就是jdk动态代理的父类接口不过是让底层自己取找
//        enhancer.setCallback( this);//jdk动态代理的
//        Object proxy=enhancer.create();
//        return proxy;
//
//
//    }
}
