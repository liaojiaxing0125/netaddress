package com.it.httpServer1.servlet;

import com.it.httpServer1.servlet.http.YcHttpServletResponse;

public class StaticProcessor implements Processor {
    @Override
    public void process(YcServletRequest request, YcServletResponse response) {
       //不应该依赖具体的类，依赖抽象的父类
        response.send();

    }
}
