package com.it;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class client {


    public static void main(String[] args)  {
        Socket localhost = null;
        try {
            localhost = new Socket("localhost", 1000);
            System.out.println("连接成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStream iis=null;
        try {
            //socket获取输入流
            iis=localhost.getInputStream();
            byte [] bs=new byte[1024];
            int length=0;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            //不能一下子边存编写
            while((length=iis.read(bs,0,length))!=-1){
                byteArrayOutputStream.write(bs,0,length);
//                String str=new String(bs,0,length,"UTF-8");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
