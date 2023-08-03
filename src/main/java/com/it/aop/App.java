package com.it.aop;

import com.google.gson.internal.$Gson$Preconditions;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {
        ApplicationContext ac=new AnnotationConfigApplicationContext(Config.class);
        OrderBiz ob=ac.getBean(OrderBiz.class);
        ob.makeOrder(1,99);
        ob.findOrderId("apple");
        ob.makeOrder(1,9999);
    }
}
