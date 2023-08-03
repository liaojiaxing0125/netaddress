package com.it.ATM;

import java.util.Properties;

/**
 * 读取配置文件且只需读取一次即可   --》单例设计模式  --》饿汉式
 *
 * @author Administrator
 */
public class MyProperties extends Properties {

	private static final long serialVersionUID = 2809456923943821836L;
	private static volatile MyProperties instance;

    private MyProperties() {
        //加载文件
        try {
            this.load(MyProperties.class.getClassLoader().getResourceAsStream("db.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static MyProperties getInstance() {
        if (instance==null){
            synchronized (MyProperties.class){
                if (instance==null){
                    instance=new MyProperties();

                }
            }

        }
        return instance;
    }
}
