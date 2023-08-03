package com.it.httpServer1.servlet;



public interface YcServlet {
    public void init();
    public void  destory();
    public void service(YcServletRequest request, YcServletResponse response);
}
