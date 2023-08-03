package com.it.httpServer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

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
            response.send();
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

