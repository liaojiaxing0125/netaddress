package com.it.httpServer1.servlet.http;



import com.it.httpServer1.servlet.YcServlet;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//应用程序常量类
public class YcServletContext {
    


    //前面是url路径
    public   static Map<String,Class> servletClass=new ConcurrentHashMap<>();
    public static   Map<String, YcServlet> servletInstance= new ConcurrentHashMap<>();

}
