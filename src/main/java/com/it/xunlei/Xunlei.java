package com.it.xunlei;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Xunlei {
    private Logger logger= Logger.getLogger(Xunlei.class);
    private static long total;
    static class LengthNotify implements Notify{
        @Override
        public void notifyResult(long length) {
            synchronized (this){
                total+=length;
            }

            System.out.println("下载了"+total+"数据");

        }
    }

    public static void main(String[] args) throws IOException {

        String url="http://pm.myapp.com/invc/xfspeed/qqpcmgr/download/QQPCDownload1530.exe";
        long fileSize=getDownloadFileSize(url);
        System.out.println("待下载文件"+url+"文件大小:"+fileSize);
        String fileName=getFileName(url);
        String newAddress=getNewFileAddress(fileName);
        RandomAccessFile raf=new RandomAccessFile(newAddress,"rw");
        raf.setLength(fileSize);//创建指定文件大小
        raf.close();
        System.out.println("文件已经创建了"+raf.getFD());
        int threadSize=Runtime.getRuntime().availableProcessors();
        long   sizePerThread=getSizePerThread(fileSize,threadSize);
        Notify notify=new LengthNotify();
        for (int i=0;i<threadSize;i++){
            DownloadTask task=new DownloadTask(i,fileSize,threadSize,sizePerThread,url,newAddress,notify);
            Thread thread=new Thread(task);
            thread.start();


        }



    }
    private static long getSizePerThread(long fileSize ,int ThreadSize){
        //如果超时会关闭
        return fileSize%ThreadSize==0?fileSize/ThreadSize:fileSize/ThreadSize+1;


    }
    private static  String getNewFileAddress(String fileName){
        String userhome=System.getProperty("user.home");
        return userhome+File.separator+fileName;


    }
    //文件名字
    private static String getFileName(String url){
        Date d=new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String fileName=sdf.format(d);
        String suffix=url.substring(url.lastIndexOf("."));
        String newFileName=fileName+suffix;
        return newFileName;


    }
    //获取文件大小
    private static long getDownloadFileSize(String url){
        long fileSize=0;


        try {
            URL u=new URL(url);
            HttpURLConnection con= (HttpURLConnection) u.openConnection();
            con.setRequestMethod("HEAD");
            con.setConnectTimeout(3000);//超时
            con.connect();
            fileSize=con.getContentLength();
        }catch (Exception e) {
            e.printStackTrace();
        }


        return fileSize;


    }
}
interface  Notify{
    public void notifyResult(long length);





}
