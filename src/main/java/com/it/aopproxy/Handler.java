package com.it.aopproxy;

import org.omg.CORBA.ObjectHelper;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;

public class Handler implements InvocationHandler {
    private Object hello;

    public Handler(Object hello) {
        this.hello = hello;
    }
    //生成代理类对象
    public Object createProxy(){
        Object proxy=Proxy.newProxyInstance(Handler.class.getClassLoader(),hello.getClass().getInterfaces(),this);
     return  proxy;

    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      Object returnvalue=method.invoke(hello,args);
      return returnvalue;
    }
}
