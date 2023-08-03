package com.it.httpServer1;

import com.it.httpServer1.servlet.DynamicProcessor;
import com.it.httpServer1.servlet.Processor;
import com.it.httpServer1.servlet.StaticProcessor;
import com.it.httpServer1.servlet.http.YcServletContext;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.sql.PreparedStatement;

public class TaskServer implements Runnable {
    private Socket s;
    private InputStream iis;
    private OutputStream   oos;
    private boolean flag=true;


    public TaskServer(Socket s) {
        this.s = s;
        try {
            this.iis=s.getInputStream();
            this.oos=s.getOutputStream();
        }catch (Exception e){
            e.printStackTrace();
            flag=false;

        }

    }

    @Override
    public void run() {
        if (flag){
            YcHttpServletRequest request=new YcHttpServletRequest(this.s,this.iis);
            YcHttpServletResponse response=new YcHttpServletResponse(request,this.oos);
//            response.send();
            Processor processor=null;
            int contentPathLength=request.getContextPath().length();
            String uri=request.getRequestURI().substring(contentPathLength);
            if (YcServletContext.servletClass.containsKey(uri)){
                processor=new DynamicProcessor();

            }   else{
                processor=new StaticProcessor();
            }
            processor.process(request, response);


        }
        try {
            this.iis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            this.oos.close();
        } catch (IOException e) {
                e.printStackTrace();
        }

    }

    }

