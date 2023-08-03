package com.it.Springyuan;

import com.it.Springyuan.context.YcAnnotationConfigApplicationContext;
import com.it.Springyuan.context.YcApplicationContext;
import com.it.Springyuan.yc.MyConfig;

public class TestMain3 {
    public static void main(String[] args) {
        YcApplicationContext ac=new YcAnnotationConfigApplicationContext(MyConfig.class);
        /*ac.getBean("userBizImpl")*/
    }
}
