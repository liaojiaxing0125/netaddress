package com.it.telnet;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Test7_telnet_baidu {
    public static void main(String[] args) {
        try {
            Socket s=new Socket("www.baidu.com",80);
            OutputStream oos=s.getOutputStream();
            InputStream iis=s.getInputStream();
            ByteArrayOutputStream boas = new ByteArrayOutputStream();
            String http="GET / HTTP/1.0\r\n\r\n";
            oos.flush();
            oos.write(http.getBytes());
            byte[] bs=new byte[1024];
            int length=-1;
            while ((length=iis.read(bs,0,bs.length))!=-1) {
                boas.write(bs,0,length);

            }
            byte[] result=boas.toByteArray();
            String content=new String(result);
            System.out.println(content);

            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

