package com.it.ATM;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ATMClient {
    public static void main(String[] args) {
        String atmHost="localhost";
        int port=12000;
        //键盘输入
        Scanner keyboard=new Scanner(System.in);
        boolean flag=true;
        String response=null;
        try(Socket s=new Socket(atmHost,port);

            PrintWriter pw=new PrintWriter(s.getOutputStream());
            Scanner sc=new Scanner(s.getInputStream());){

          do {
              System.out.println("=====欢迎来到中国银行======");
              System.out.println("1.存");
              System.out.println("2.取");
              System.out.println("3.查询");
              System.out.println("4.退出");
              System.out.println("请输入你的操作");
              String command=keyboard.nextLine();
              if ("1".equalsIgnoreCase(command)){
                  pw.println("DEPOSITE 1 100");


              }else if ("2".equalsIgnoreCase(command)){

                  pw.println("WITHDRAW 1 5");

              }else  if ("3".equalsIgnoreCase(command)){
                  pw.println("BALANCE 1");


              }else if ("4".equalsIgnoreCase(command)){

                  pw.println("QUIT");
                  flag=false;

              }else {
                  System.out.println("无此选项");
                  continue;

              }
              pw.flush();
              response=sc.nextLine();
              System.out.println("服务器的响应为:"+response);
          }while (flag);

        }catch (Exception ex){
            ex.printStackTrace();
            System.out.println("客户端无法连接,请与维修人员联系。。。");

        }



        System.out.println("");
    }
}
