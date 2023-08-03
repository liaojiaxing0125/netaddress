package com.it.downlay;

import javax.print.DocFlavor;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class xunlei {
    public static void main(String[] args) {
        String url="http://dl.baofeng.com/baofeng5/bf5_new.exe";

    }
    public static  String getNewFileName(String url){
        Date d=new Date();
        DateFormat df=new SimpleDateFormat("yyyyMMddHHmmss");
        String prefix=df.format(d);
        String suffix=url.substring(url.lastIndexOf("."));
        return prefix+suffix;


    }
    public static  long getFileSize(String url) {
        int fileSize = 0;

        try {
            URL u = new URL(url);
            HttpURLConnection huc = (HttpURLConnection) u.openConnection();
            huc.setRequestMethod("HEAD");//发送head请求，只返回请求的基本信息
            huc.connect();
            fileSize = huc.getContentLength();//获取长度


        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileSize;

    }


}

