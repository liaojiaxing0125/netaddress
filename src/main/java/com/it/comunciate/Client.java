package com.it.comunciate;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket sc=new Socket("localhost",12000);
        System.out.println(sc.getRemoteSocketAddress());
        //system.in标准输入流
        //字符输入流
        try (
                Scanner keyboard=new Scanner(System.in);

                Scanner clientReader = new Scanner(sc.getInputStream());
                PrintWriter pw=new PrintWriter(sc.getOutputStream());) {


            do {
                System.out.println("请输入客户端想对服务器说的话");
                String line = keyboard.nextLine();
                pw.println(line);
                pw.flush();
                if ("bye".equalsIgnoreCase(line)) {
                    System.out.println("客户端主动断开连接");

                }
                //取服务器的响应
                String responese = clientReader.nextLine();
                System.out.println("服务器的响应为:" + responese);
                if ("bye".equalsIgnoreCase(responese)) {
                    System.out.println("服务器主动断开");
                    break;

                }
            } while (true);
            System.out.println("客户端正常结束");
        }catch (Exception e){
            e.printStackTrace();

        }

    }
}
