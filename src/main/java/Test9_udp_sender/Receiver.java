package Test9_udp_sender;

import com.sun.org.apache.xpath.internal.operations.String;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class Receiver {
    public static void main(String[] args) throws IOException {
        byte[]bs=new byte[1024];
        DatagramPacket dp=new DatagramPacket(bs,bs.length);
        DatagramSocket ds=new DatagramSocket(20000);
        while(true){
            ds.receive(dp);

            System.out.println( bs);

        }


    }
}
