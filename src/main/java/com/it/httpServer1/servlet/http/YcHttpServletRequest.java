package com.it.httpServer1.servlet.http;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.InputStream;
import java.net.Socket;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;

public class YcHttpServletRequest {
    private Logger logger= Logger.getLogger(YcHttpServletRequest.class);
    private InputStream iis;
    //GET POST请求
    private String method;
    private Socket s;
    //定位符这个是在本地的
    private String  requestURL;
    //万维网上路劲
    private String requestURI;
    //上下文路径
    private String contextPath;
    //请求地址参数
    private String queryString;
    //请求参数
    private Map<String,String[]> paramterMap=new ConcurrentHashMap<>();
    //请求协议类型
    private String scheme;
    //协议版本
    private String protocol;
    //项目真实路径
    private String realPath;

    public YcHttpServletRequest(Socket s,   InputStream iis) {
        this.iis = iis;

        this.s=s;
        this.parseRequest();
    }
    private void parseRequest() {
        String requestInfoString=readForInputStream();
        if (requestInfoString==null||"".equalsIgnoreCase(requestInfoString.trim())){
            throw new RuntimeException("输入流有异常");
        }
        parseRequestInfoString(requestInfoString);
    }
    private void    parseRequestInfoString(String requestInfoString){
        //将字符串按空格分开
        StringTokenizer st=new StringTokenizer(requestInfoString);
        this.method=st.nextToken();
        //地址栏的参数
        this.requestURI=st.nextToken();
        int questionIndex=this.requestURI.lastIndexOf("?");
        if (questionIndex>=0){
            this.requestURI=this.requestURI.substring(0,questionIndex);
            this.queryString=this.requestURI.substring(questionIndex+1);

        }
        this.protocol=st.nextToken();
        this.scheme=this.protocol.substring(0,this.protocol.indexOf("/"));
        //从第一个开始
        int slash2Index=this.requestURI.indexOf("/",1);
        if(slash2Index>0){
            this.contextPath=this.requestURI.substring(0,slash2Index);
        }else{
            this.contextPath=this.requestURI;
        }
        this.requestURL=this.scheme+"://"+this.s.getLocalSocketAddress()+this.requestURI;

        //参数处理:dadada/index.html?uname=?&pwd=?
        if (this.queryString!=null&&this.queryString.length()>0){
            String[]ps=this.queryString.split("&");
            for (String a:ps){

                String[]params=a.split("=");
                this.paramterMap.put(params[0],params[1].split(","));

            }


        }
        //静态资源的位置
        this.realPath=System.getProperty("user.dir")+ File.separator+"webapps";





    }
    private String readForInputStream(){
        int length=-1;
        byte[]bs=new byte[300*1024];
        StringBuffer sb=null;
        try {
            length=this.iis.read(bs,0,bs.length);
            sb=new StringBuffer();
            for (int i=0;i<length;i++){
                sb.append((char)bs[i]);

            }


        }catch (Exception e){
            e.printStackTrace();
            logger.error("读取请求消息失败");

        }
        System.out.println(sb.toString());
        return sb.toString();






    }

    public InputStream getIis() {
        return iis;
    }

    public String getMethod() {
        return method;
    }

    public String getRequestURL() {
        return requestURL;
    }

    public String getRequestURI() {
        return requestURI;
    }

    public String getContextPath() {
        return contextPath;
    }

    public String getQueryString() {
        return queryString;
    }

    public Map<String, String[]> getParamterMap() {
        return paramterMap;
    }

    public String getScheme() {
        return scheme;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getRealPath() {
        return realPath;
    }


}
