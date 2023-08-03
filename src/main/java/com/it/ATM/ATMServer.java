package com.it.ATM;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ATMServer {
    public static void main(String[] args) {
        //IO密集
        int processsors=Runtime.getRuntime().availableProcessors()*30;
        ServerSocket ss= null;
        try {
            ss = new ServerSocket(12000);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("服务器"+ss.getLocalSocketAddress()+"正常启动");
        boolean flag=true;
        while (flag){
            Socket s=null;
            try {
                s=ss.accept();
                System.out.println("客户机"+s.getRemoteSocketAddress()+"连接到服务器");
                BankTask bt=new BankTask(s);
                Thread t=new Thread(bt);
                t.start();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    }
}
