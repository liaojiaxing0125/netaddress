package com.it.xunlei;

import javafx.scene.input.InputMethodTextRun;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class DownloadTask  implements Runnable{
    //第几个线程
    private final int i;
    //文件大小
    private final  long fileSize;
    //线程数量
    private final int threadSize;
    //每个线程要求大小
    private final  long sizePerThread;
    //下载文件的地方
    private final  String url;
    //文件地址的
    private  final  String newAddress;
    private final Notify notify;

    public DownloadTask(int i, long fileSize, int threadSize, long sizePerThread, String url, String newAddress,Notify notify) {
        this.i = i;
        this.fileSize = fileSize;
        this.threadSize = threadSize;
        this.sizePerThread = sizePerThread;
        this.url = url;
        this.newAddress = newAddress;
        this.notify=notify;
    }

    @Override
    public void run() {
        long start=i*sizePerThread;
        long end=(i+1)*sizePerThread-1;
        RandomAccessFile raf=null;
        InputStream iis=null;
        try {
            raf = new RandomAccessFile(newAddress, "rw");
            //从指定位置开始写入
            raf.seek(start);
            URL urlobj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) urlobj.openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(10 * 1000);
            con.setRequestProperty("Range", "bytes=" + start +"-"+ end);
            iis = new BufferedInputStream(con.getInputStream());
            byte[] bs = new byte[1024 * 100];
            int length = -1;
            while ((length = iis.read(bs, 0, bs.length)) != -1) {
                raf.write(bs, 0, length);
                if (this.notify!=null){
                    notify.notifyResult(length);
                }
            }

        }catch (Exception e){
            e.printStackTrace();



        }finally {
            if (raf!=null){
                try {
                    raf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (iis!=null){
                try {
                    iis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }





    }
}
