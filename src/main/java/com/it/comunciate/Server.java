package com.it.comunciate;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.*;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket ss=null;
        ss=new ServerSocket(12000);
        System.out.println(ss.getInetAddress()+"启动了");
        while (true){
            Socket s=ss.accept();
            TalkTaskto task=new TalkTaskto(s);
            new Thread(task).start();
        }
        
    }
    static class TalkTaskto implements Runnable{
        private Socket s;

        public TalkTaskto(Socket s) {
            this.s = s;
        }

        @Override
        public void run() {
            try( Scanner clientReader = new Scanner(s.getInputStream());
                 PrintWriter pw = new PrintWriter(s.getOutputStream());){
                do {
                    String response=clientReader.nextLine();
                    System.out.println("客户端"+s.getRemoteSocketAddress()+"对server说:"+response);
                    if ("bye".equalsIgnoreCase(response)){
                        System.out.println("客户端断开");
                        break;

                    }
                    String line=getResponse(response);
                    pw.println(line);
                    pw.flush();
                    if ("bye".equalsIgnoreCase(line)){
                        System.out.println("服务器");
                        break;

                    }

                }while (true);
                System.out.println("服务器断开");
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        private String getResponse(String msg) throws IOException {
            URL url=new URL("http://api.qingyunke.com/api.php?key=free&appid=0&msg="+msg);
            HttpURLConnection con= (HttpURLConnection) url.openConnection();
            try (
                    InputStream iis=con.getInputStream();

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ) {

                byte[] bs = new byte[1024];
                int length;
                while ((length = iis.read(bs, 0, bs.length)) != -1) {
                    baos.write(bs, 0, length);

                }
                baos.flush();
                byte[] result = baos.toByteArray();
                String s = new String(result);
                System.out.println(s);
                Gson g=new Gson();
                Result r=g.fromJson(s,Result.class);
                return r.getContent();

            }catch (Exception e){
                e.printStackTrace();

            }
            return "";
        }
    }
}
