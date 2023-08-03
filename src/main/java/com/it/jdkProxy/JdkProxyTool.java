package com.it.jdkProxy;

import com.sun.deploy.net.proxy.ProxyUnavailableException;
import org.omg.CORBA.OBJ_ADAPTER;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JdkProxyTool implements InvocationHandler {
    private Object obj;

    public JdkProxyTool(Object obj) {
        this.obj = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object invoke = method.invoke(obj, args);//实际调用目标类的方法
        return invoke;
    }
    public Object createProxy(){
        Object proxy= Proxy.newProxyInstance(JdkProxyTool.class.getClassLoader(),obj.getClass().getInterfaces(),this);
        return proxy;

    }
}
