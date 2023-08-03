package com.it.httpServer;

import java.io.*;
import java.net.URL;

//1XX 2Xx正常 3xx重定向 4xx资源不存在
public class  YcHttpServletResponse {
    private YcHttpServletRequest request;
    private OutputStream oos;

    public YcHttpServletResponse(YcHttpServletRequest request, OutputStream oos) {
        this.request = request;
        this.oos = oos;
    }
    public void send(){
        String url=request.getRequestURI();//shengchang/index.html
        String realPath=this.request.getRealPath();
        File  f=new File(realPath,url);
        byte[] fileContent=null;
        String responseProtocol=null;
        if (!f.exists()){
            //文件不存在
            fileContent=readFile(new File(realPath,"/404.html"));
            responseProtocol=gen404(fileContent);
        }else{
            fileContent=readFile(new File(realPath,url));
            responseProtocol=gen200(fileContent);


        }
        try {
            oos.write(responseProtocol.getBytes());
            oos.flush();
            oos.write(fileContent);
            oos.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (oos!=null){
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }



    }

    private String gen200(byte[] fileContent) {
        String protocol200="";
        String url=this.request.getRequestURI();
        int index=url.lastIndexOf(".");
        if (index>0){
            index=index+1;

        }
        String fileExtension=url.substring(index);
        if ("JPG".equalsIgnoreCase(fileExtension)){
            protocol200="HTTP/1.1 200 OK\r\nContent-Type: image/jpeg\r\nContent-Length: "+fileContent.length+"\r\n\r\n";

        }else if("css".equalsIgnoreCase(fileExtension)){

            protocol200="HTTP/1.1 200 OK\r\nContent-Type: text/css\r\nContent-Length: "+fileContent.length+"\r\n\r\n";

        }else if("js".equalsIgnoreCase(fileExtension)){

            protocol200="HTTP/1.1 200 OK\r\nContent-Type: application/javascript\r\nContent-Length: "+fileContent.length+"\r\n\r\n";

        }else if("gif".equalsIgnoreCase(fileExtension)){

            protocol200="HTTP/1.1 200 OK\r\nContent-Type: image/gif\r\nContent-Length: "+fileContent.length+"\r\n\r\n";

        } else if("png".equalsIgnoreCase(fileExtension)){

            protocol200="HTTP/1.1 200 OK\r\nContent-Type: image/png\r\nContent-Length: "+fileContent.length+"\r\n\r\n";

        }else{

            protocol200="HTTP/1.1 200 OK\r\nContent-Type: text/html; charset=utf-8\r\nContent-Length: "+fileContent.length+"\r\n\r\n";
        }
        return protocol200;
    }

    private String gen404(byte[] fileContent) {
        //注意空格子
        String protocol404="HTTP/1.1 404 Not Found\r\nContent-Type: text/html;charset= utf-8\r\nContent-Length: "+fileContent.length+"\r\n";
        protocol404+="Server:Kitty server\r\n\r\n";
        return protocol404;

    }

    private byte[] readFile(File file) {
        ByteArrayOutputStream boas = new ByteArrayOutputStream();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            byte[] bs = new byte[100 * 1024];
            int length = -1;
            while ((length = fis.read(bs, 0, bs.length)) != -1) {

                boas.write(bs, 0, length);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return boas.toByteArray();


    }
}
