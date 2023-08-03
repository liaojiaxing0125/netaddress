package com.it.httpServer1.servlet.http;


import com.it.httpServer1.YcHttpServletRequest;
import com.it.httpServer1.YcHttpServletResponse;
import com.it.httpServer1.servlet.YcServlet;
import com.it.httpServer1.servlet.YcServletRequest;
import com.it.httpServer1.servlet.YcServletResponse;


public abstract class YcHttpServlet implements YcServlet {
    @Override
    public void init() {

    }
    protected void doPost(YcHttpServletRequest req, YcHttpServletResponse resp){


    }
    protected void doGet(YcHttpServletRequest req, YcHttpServletResponse resp){


    }
    protected void doHead(YcHttpServletRequest req, YcHttpServletResponse resp){


    }
    protected void doDelete(YcHttpServletRequest req, YcHttpServletResponse resp){


    }
    protected void doTrace(YcHttpServletRequest req, YcHttpServletResponse resp){


    }
    protected void doOption(YcHttpServletRequest req, YcHttpServletResponse resp){


    }


    @Override
    public void destory() {

    }

    @Override
    public void service(YcServletRequest request, YcServletResponse response) {
        //request中method是http特有的
        String method=((YcHttpServletRequest)request).getMethod();
        if ("get".equalsIgnoreCase(method)){
            doGet( (YcHttpServletRequest)request,(YcHttpServletResponse) response);


        }else if("post".equalsIgnoreCase(method)){
            doPost((YcHttpServletRequest)request,(YcHttpServletResponse) response);

        }else if ("head".equalsIgnoreCase(method)){
            doHead((YcHttpServletRequest)request,(YcHttpServletResponse) response);
        }else if ("trace".equalsIgnoreCase(method)){
            doTrace((YcHttpServletRequest)request,(YcHttpServletResponse) response);
        }else if ("delete".equalsIgnoreCase(method)){
            doDelete((YcHttpServletRequest)request,(YcHttpServletResponse) response);

        }else if ( "option".equalsIgnoreCase(method)){
            doOption((YcHttpServletRequest)request,(YcHttpServletResponse) response);


        }else{

            //错误协议
        }

    }
}
