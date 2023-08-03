package com.it.httpServer1;




import com.it.httpServer1.servlet.YcServlet;
import com.it.httpServer1.servlet.YcWebServlet;
import com.it.httpServer1.servlet.http.YcServletContext;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.BeanDefinition;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;

public class TomcatServer {
    static Logger  logger =Logger.getLogger(TomcatServer.class);

    public static void main(String[] args) {

        logger.debug("启动了");
        TomcatServer ts=new TomcatServer();
        int port=ts.parsePortForXml();
        ts.startServer(port);
        logger.debug("启动了");
    }
  
    private void startServer(int port){
        boolean flag=true;
        //jvm类加载器获取target下面的路径classes路径
        String packageName="com.it.httpServer1";
        String packagePath=packageName.replaceAll("\\.","/");
        try {
            Enumeration<URL> files  =Thread.currentThread().getContextClassLoader().getResources(packagePath);
            while(files.hasMoreElements()){
                URL url=files.nextElement();
                logger.debug("正在打包路径为"+url.getFile());
                findPackageClasses(url.getFile(),packageName);

            }
        }catch (Exception e){
            e.printStackTrace();
        }

        try ( ServerSocket ss=new ServerSocket(port);) {
//可以读取server.xml开始是否读取成功
            while (flag) {
                try {
                    Socket s = ss.accept();
                    logger.debug("正常启动" + port);
                    TaskServer task = new TaskServer(s);
                    Thread thread = new Thread(task);
                    thread.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("服务器套接字失败");
        }

    }
//参数：包名加上路径/com/yc
        //com.yc
    private void findPackageClasses(String packagePath, final String packageName) {
        if (packagePath.startsWith("/")){
            packagePath=packagePath.substring(1);

        }
        //取这个文件路径所有的结果
        File file=new File(packagePath);
       File[] classFiles=file.listFiles(new FileFilter() {
           @Override
           public boolean accept(File pathname) {
                   if (pathname.getName().endsWith(".class")||pathname.isDirectory()){
                       return true;


                   }else{
                       return  false;
                   }
           }


        });
       if (classFiles!=null&&classFiles.length>0){
           for (File  cf:classFiles){
               if (cf.isDirectory()){
                   findPackageClasses(cf.getAbsolutePath(),packageName+"."+cf.getName());//com.it.ATM

               }else{
                   //字节码文件，则利用类加载字节码文件
                   try {
                       URLClassLoader uc=new URLClassLoader(new URL[]{});
                       //问题
                       Class cls=uc.loadClass(packageName+"."+cf.getName().replaceAll(".class",""));
                      //查看是否有注解
                       if (cls.isAnnotationPresent(YcWebServlet.class)){

                           //神奇
                           YcWebServlet anno= (YcWebServlet) cls.getAnnotation(YcWebServlet.class);
                           String url = anno.value();
                           YcServletContext.servletClass.put(url,cls);

                           logger.info("加载一个类"+cls.getName());
                       }
                   }catch (Exception e){
                       e.printStackTrace();

                   }


               }

           }

       }





    }

    private  int parsePortForXml(){
        int port=8080;
        //类加载获取文件路径为target下面的文件的classes文件src的类加载才在
        //TomcatServer.class.getClassLoader().getResourceAsStream()

        String serverXml=System.getProperty("user.dir")+ File.separator+"conf"+ File.separator+"server.xml";
        try (InputStream iis=new FileInputStream(serverXml)){
            DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            Document doc= documentBuilder.parse(iis);
            NodeList nl=doc.getElementsByTagName("Connector");
            for (int i=0;i<nl.getLength();i++){
                Element   node= (Element) nl.item(i);
                port=Integer.parseInt(node.getAttribute("port"));


            }



        }catch (Exception e){
            e.printStackTrace();

        }
        return port;





    }
}
