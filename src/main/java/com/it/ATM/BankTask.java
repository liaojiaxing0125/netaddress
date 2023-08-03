package com.it.ATM;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class BankTask implements Runnable {
    private Socket s;
    private boolean flag=true;
    private DbHelper db=new DbHelper();
    public BankTask(Socket s){

        this.s=s;
    }
    @Override
    public void run() {
        try (
                Socket s=this.s;
                Scanner reader=new Scanner(s.getInputStream());
                PrintWriter pw=new PrintWriter(s.getOutputStream());

                ){
            BankAccount ba=null;
            while (flag){
                if (!reader.hasNext()){
                    System.out.println("客户端"+s.getRemoteSocketAddress()+"掉线");
                    break;

                }
                String command=reader.next();
                if ("DEPOSITE".equalsIgnoreCase(command)){
                    int id=reader.nextInt();
                    double money=reader.nextDouble();
                    String sql="update bank set balance=balance+? where id = ? ";
                    db.update(sql,money,id);
                    sql="select  * from bank where id =? ";
                    ba= db.finds(sql, BankAccount.class, id).get(0);




                }else if ("WITHDRAW".equalsIgnoreCase(command)){

                    int id=reader.nextInt();
                    double money=reader.nextDouble();
                    String sql="select * from where id= ?";
                    ba=db.finds(sql,BankAccount.class,id).get(0);
                    if (ba.getBalance()<money){
                        pw.println("余额不足");
                        pw.flush();
                        continue;

                    }
                    sql="update bank set balance=balance-? where id =?";
                    db.update(sql,money,id);
                    ba.setBalance(ba.getBalance()-money);

                }else if ("BALANCE".equalsIgnoreCase(command)){
                    int id=reader.nextInt();
                    String sql="select * from bank where id = ?";
                    ba=db.finds(sql,BankAccount.class,id).get(0);

                }else if ("QUIT".equalsIgnoreCase(command)){
                    System.out.println("客户端");
                    break;

                }else {
                    //错误命令
                    pw.println("系统暂不知此命令"+command);
                    pw.flush();
                    continue;

                }
                pw.println(ba.getId()+""+ba.getBalance());
                pw.flush();



            }
        }catch (Exception e){


        }



    }
}
