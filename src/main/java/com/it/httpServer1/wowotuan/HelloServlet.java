package com.it.httpServer1.wowotuan;

import com.google.gson.internal.bind.util.ISO8601Utils;
import com.it.httpServer1.YcHttpServletRequest;
import com.it.httpServer1.YcHttpServletResponse;
import com.it.httpServer1.servlet.YcWebServlet;
import com.it.httpServer1.servlet.http.YcHttpServlet;
import sun.misc.FpUtils;

import javax.servlet.annotation.WebServlet;
import java.io.PrintWriter;

@YcWebServlet("/hello")
public class HelloServlet extends YcHttpServlet {
    @Override
    public void init() {
        System.out.println("HelloServlet初始化方法");
    }

    @Override
    protected void doGet(YcHttpServletRequest req, YcHttpServletResponse resp) {
        System.out.println("HelloServlet");
        String result="hello 你好";
        PrintWriter out=resp.getWriter();
        out.println("HTTP/1.1 200 OK\r\nContent-Type: text/html; charset=utf-8\r\nContent-Length: "+result.getBytes().length+"\r\n\r\n");
        out.println(result);
        out.flush();
        
    }
}
