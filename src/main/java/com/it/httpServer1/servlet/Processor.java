package com.it.httpServer1.servlet;

public interface Processor {
    //处理方法
    public void process(YcServletRequest request,YcServletResponse response);
}
