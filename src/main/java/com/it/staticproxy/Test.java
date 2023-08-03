package com.it.staticproxy;

import org.springframework.core.annotation.Order;

public class Test {
    public static void main(String[] args) {
        OrderBiz ob=new StaticProxy(new OrderBizImpl());
        ob.addOrder(1,100);
    }
}
