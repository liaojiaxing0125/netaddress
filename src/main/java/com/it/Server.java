package com.it;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class Server {
    public static void main(String[] args) {

         ServerSocket server = null;
        for (int i = 0; i < 10000; i++) {
            try {

                server = new ServerSocket(i);
                System.out.println("端口" + server.getLocalPort());


            } catch (IOException e) {
                continue;

            }
            break;
        }
        Socket accept = null;
        while (true) {
            try {
                accept = server.accept();//阻塞式端口
                System.out.println("有一个客户端已经连上" + accept.getRemoteSocketAddress());
                OutputStream outputStream = accept.getOutputStream();
//                byte[] bs = getContent();
//                outputStream.write(bs);


//                SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
//                String time=simpleDateFormat.format(new Date());
//                outputStream.write(time.getBytes());
                outputStream.flush();


            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    accept.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    private static byte[] getContent() {
        final String path = System.getProperty("user.dir") + "/target/classes";
        File f = new File(path);
        File[] files = f.listFiles(new FileFilter() {
            public boolean accept(File pathname) {
                if (pathname.isFile() && pathname.getName().endsWith(".txt")) {
                    return true;

                }
                return false;
            }
        });

        Random r = new Random();
        int index = r.nextInt(files.length);
        File filetoread = files[index];
        try {
            FileInputStream fileInputStream = new FileInputStream(filetoread);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            {

                byte[] bytes = new byte[1024];
                int length = -1;
                while ((length = fileInputStream.read(bytes, 0, length)) != -1) {
                    byteArrayOutputStream.write(bytes, 0, length);

                }
                return byteArrayOutputStream.toByteArray();


            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }
}











