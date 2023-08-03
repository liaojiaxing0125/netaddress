package com.it.httpServer1.servlet;

import com.it.httpServer1.YcHttpServletRequest;
import com.it.httpServer1.servlet.http.YcServletContext;

import java.io.PrintWriter;

public class DynamicProcessor implements Processor {
    @Override
    public void process(YcServletRequest request, YcServletResponse response) {
        //从request中取出requestURI/hello
        String uri=((YcHttpServletRequest)request).getRequestURI();
        int contentPathLength=((YcHttpServletRequest)request).getContextPath().length(); uri=((YcHttpServletRequest)request).getRequestURI().substring(contentPathLength);
        YcServlet   servlet=null;
        //为了查阅先看map里面class实例，如有说明是第二次访问
        try {
            if (YcServletContext.servletInstance.containsKey(uri)){
                servlet=YcServletContext.servletInstance.get(uri);

            }else{
                //没有表明是第一次返回问,先利用反射创建servlet无参构造 存在另一个map里面调用init()
                Class cls=YcServletContext.servletClass.get(uri);
                Object obj= null;

                obj = cls.newInstance();

                if (obj instanceof YcServlet){
                    servlet= (YcServlet) obj;
                    servlet.init();
                    YcServletContext.servletInstance.put(uri,servlet);

                }

            }
            servlet.service(request,response);//YcHttpServlet.service()

        }catch (Exception e){
            String bobyEntity=e.toString();
            String protocol=gen500(bobyEntity);
            PrintWriter out=response.getWriter();
            out.println(protocol);
            out.println(bobyEntity);
            out.flush();


        }


    }

    private String gen500(String bobyEntity) {
        String protocol="HTTP/1.1 500 Internal Sever Error\r\nContent-Type: image/jpeg\r\nContent-Length: "+bobyEntity.getBytes().length+"\r\n\r\n";
        return protocol;
    }
}
