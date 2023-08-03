package Test9_udp_sender;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

public class Sender {
    public static void main(String[] args) throws IOException {
        String s="hello world";
        DatagramPacket datagramPacket = new DatagramPacket(s.getBytes(),s.getBytes().length,new InetSocketAddress("localhost",20000));
        DatagramSocket ds=new DatagramSocket(1890);
        ds.send(datagramPacket);
        ds.close();
        System.out.println("发送udp数据包成功");


    }


}
