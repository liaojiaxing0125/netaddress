package com.it.jdkProxy;

public class Test2 {
    public static void main(String[] args) {
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles","true");
        JdkProxyTool jpt=new JdkProxyTool(new OrderBizImpl());
        OrderBiz ob= (OrderBiz) jpt.createProxy();
        System.out.println("生成代理类得对象"+ob);

    }
}
