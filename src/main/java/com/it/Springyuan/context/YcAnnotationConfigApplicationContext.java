package com.it.Springyuan.context;




import com.it.Springyuan.annotation.*;
import com.mysql.cj.conf.ConnectionPropertiesTransform;
import com.sun.org.apache.xml.internal.security.transforms.params.XPathFilterCHGPContainer;
import javafx.beans.binding.ObjectExpression;
import org.omg.CORBA.ObjectHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.util.resources.cldr.zh.CalendarData_zh_Hans_HK;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

public class YcAnnotationConfigApplicationContext implements YcApplicationContext {
private Logger logger=LoggerFactory.getLogger(YcAnnotationConfigApplicationContext.class);
    //存每个 待托管的bean定义信息
    private Map<String,YcBeanDefinition> beanDefinitionMap=new HashMap<>();
    //存每个 实例化后的bean
    private Map<String,Object> beanMap=new HashMap<>();
    //存系统属性
    private Properties pros;
    public YcAnnotationConfigApplicationContext(Class... configClass){
        //读取系统的属性 存好
        try {
            pros=System.getProperties();
            List<String> toScanPackagePath=new ArrayList<>();
            for (Class cls:configClass){
                if (cls.isAnnotationPresent(YcConfiguration.class)==false){
                    continue;

                }
                String[] basePackages=null;
                if (cls.isAnnotationPresent(YcComonentScan.class)){
                    //如果,则说明此配置类上有@YcComponentScan,则读取basePackages
                    YcComonentScan ycComponentScan = (YcComonentScan) cls.getAnnotation(YcComonentScan.class);
                    //获取basePackages的值
                    basePackages=ycComponentScan.basePackages();

                    //当本类中的basePackages没有属性的时候
                    if (basePackages==null||basePackages.length<=0){
                        //创建一个string[]数组
                        basePackages=new String[1];

                        //默认值为该类的所在的包的同步目录
                        basePackages[0]=cls.getPackage().getName();
                    }
                    logger.info(cls.getName()+"类上有@YcComponentScan"+basePackages[0]);


                }
                //开始扫描这些basepackages的包下的bean,并加载包完成 BeanDefinition对象，存在beanDefiniton
                recursiveLoadBeanDefinition(basePackages);


            }
            //循环beandefinitionmap，创建bean(是否为懒加载,是单例)存到beanMap
            createBean();
            //所有托管的beanmap的bean,在属性和方法上是否为@Autowired @Resource@Value
            doDi();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        //扫描配置类上的Scan注解包
        //将所有的类以键值的形式放在map里面

    }

    /**
     *
     * 开始扫描这些basepackages包下的bean,并加载包装成beandefinition的对象
     */

    private void doDi() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        //循环beanMap 这是托管Bean
        for (Map.Entry<String,Object> entry:beanMap.entrySet()){
            String beanId=entry.getKey();
            Object beanObj=entry.getValue();
            //情况一:属性上有，@YcResource注解情况
            //情况二:方法上有@YcResource注解情况
            //情况三:构造方法上有@YcResource的注解情况
            Field[] fields=beanObj.getClass().getDeclaredFields();
            for (Field field:fields){
               if (field.isAnnotationPresent(YcResource.class)){
                   YcResource ycResource=field.getAnnotation(YcResource.class);
                   String toDiBeanId=ycResource.name();
                   Object obj=getToDiaBean(toDiBeanId);
                   //注入
                   field.setAccessible(true);//因为属性为private,所以要将它的accessible设为true
                   field.set(beanObj,obj);//userBizImpl.userdao=userdaoImpl

               }

            }

        }

    }
    private Object getToDiaBean(String toDiBeanId) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        if (beanMap.containsKey(toDiBeanId)){
            return beanMap.get(toDiBeanId);


        }
        if (!beanDefinitionMap.containsKey(toDiBeanId)){


            throw  new RuntimeException("Spring容器没有加载此class");
        }
        YcBeanDefinition bd=beanDefinitionMap.get(toDiBeanId);
        if (bd.isLazy()){
            //是因为懒,所有没有托管
            String classpath=bd.getClassInfo();
            Object beanObj =Class.forName(classpath).newInstance();
            beanMap.put(toDiBeanId,beanObj);
            return beanObj;

        }
        //是否因为prototype
        if (bd.getScope().equalsIgnoreCase("prototype")){
            //是因为懒,没有托管
            String classpath=bd.getClassInfo();
            Object beanObj=Class.forName(classpath).newInstance();
            //原型模式每次都要创建新的对象
            return  beanObj;

        }
        return null;


    }
    /**
    *
    *
    * 循环beanDefinitionMap创建bean(是否为懒加载,是单例),存在beanMap
    *
    * */



    private void createBean() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        for (Map.Entry<String,YcBeanDefinition> entry:beanDefinitionMap.entrySet()){
            String beanId=entry.getKey();
            YcBeanDefinition ybd=entry.getValue();
            if (!ybd.isLazy()&&!ybd.getScope().equalsIgnoreCase("prototype")){
                String classinfo=ybd.getClassInfo();
                Object obj=Class.forName(classinfo).newInstance();
                beanMap.put(beanId,obj);
                logger.trace("spring容器托管了"+beanId+"beanid"+classinfo);

            }

        }
    }

    private void recursiveLoadBeanDefinition(String[] basePackages) {
        for (String basePackage : basePackages) {
            //将包名中的.替换成路径中的/
            String packagePath=basePackage.replaceAll("\\.","/");
            //target/classes /com/yc
            //Enumeration集合 url:每个资源的路径
            Enumeration<URL> files=null;
            try {
                 files = Thread.currentThread().getContextClassLoader().getResources(packagePath);
               //循环这个files的,看是否存在我要加载的资源
                 while(files.hasMoreElements()){
                     URL url=files.nextElement();
                     logger.trace("当前类的加载"+url.getFile());
                     //查看此包下的类 com/yc/全路径    com/yc/包名
                     findPackageClasses(url.getFile(),basePackage);
                 }
            } catch (IOException e) {
                e.printStackTrace();
            }



        }


    }

    private void findPackageClasses(String packagePath, String basePackage) {
        //路径异常的处理,前面有/,则去掉它
        if (packagePath.startsWith("/")){
            packagePath=packagePath.substring(1);

        }
        //取这个路径下所有的字节码文件(因为目录下有可能有其他的资源)
        File file=new File(packagePath);
        //只取后缀名字为.class字节码,排除一些图片
        File [] classFile=file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                if (pathname.getName().endsWith(".class") || pathname.isDirectory()) {
                    return true;

                }
                return false;

            }
        });
        if (classFile==null||classFile.length<=0){
            return;
        }
        for (File cf:classFile){
            if (cf.isDirectory()){
                findPackageClasses(cf.getAbsolutePath(),basePackage+'.'+cf.getName());

            }else{
                //是class文件,判断此文件对应的class是否有@comonent注解
                URLClassLoader uc=new URLClassLoader(new URL[]{});
                Class cls=null;
                try {
                    cls=uc.loadClass(basePackage+"."+cf.getName().replaceAll(".class",""));
                    if (cls.isAnnotationPresent(YcComponent.class)||cls.isAnnotationPresent(YcConfiguration.class)||cls.isAnnotationPresent(YcController.class)||cls.isAnnotationPresent(YcRepository.class)||cls.isAnnotationPresent(YcService.class)){
                        logger.info("加载到一个托管的类"+cls.getName());
                        //TODO:包装成beanDefiniton文件
                        YcBeanDefinition bd=new YcBeanDefinition();
                       if (cls.isAnnotationPresent(YcLazy.class)){
                           bd.setLazy(true);


                       }
                       if (cls.isAnnotationPresent(YcScope.class)){
                           YcScope ycScope= (YcScope) cls.getAnnotation(YcScope.class);
                           String scope=ycScope.value();
                           bd.setScope(scope);


                       }
                       //classinfo中保存的类的包路径 com.it.dao.userDaoImpl
                        //为了后面的cls.newinstance
                       bd.setClassInfo(basePackage+"."+cf.getName().replaceAll(".class",""));
                       //存到beanDefinition中beanId-》beanDefinition对象中
                       String beanId=getBeanId(cls);
                       this.beanDefinitionMap.put(beanId,bd);


                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }


            }


        }






    }

    /**
     * 获取一个带托管的beanid
     * 1.@component 直接用类名(首字母小写)
     * 2@component("beanId")
     *
     *
     *
     */
    private String getBeanId(Class cls){

        YcComponent ycComponent= (YcComponent) cls.getAnnotation(YcComponent.class);
        YcController ycController= (YcController) cls.getAnnotation(YcController.class);
        YcService ycService= (YcService) cls.getAnnotation(YcService.class);
        YcRepository ycRepository= (YcRepository) cls.getAnnotation(YcRepository.class);
        YcConfiguration ycConfiguration= (YcConfiguration) cls.getAnnotation(YcConfiguration.class);
        if (ycConfiguration!=null){
            return cls.getSimpleName();//全路径

        }
        String beanId=null;
        if (ycComponent!=null){
            beanId=ycComponent.value();


        }else if (ycController!=null){
            beanId=ycController.value();
        }else if (ycService!=null){
            beanId=ycService.value();

        }else if (ycRepository!=null){
            beanId=ycRepository.value();
        }
        if (beanId==null||"".equalsIgnoreCase(beanId)){
            String typename=cls.getSimpleName();
            //类名首字母转小写加上
            beanId=typename.substring(0,1).toLowerCase()+typename.substring(1);

        }
        return beanId;


    }


    @Override
    public Object getBean(String beanid) {
        YcBeanDefinition bd=this.beanDefinitionMap.get(beanid);
        if (bd==null){
            throw new RuntimeException("容器没有加载地址bean");

        }
        String scope=bd.getScope();
        if ("prototype".equalsIgnoreCase(scope)){
            Object obj=null;
            try {
                obj=Class.forName(bd.getClassInfo()).newInstance();
                return obj;
            }catch (Exception e){


            }

        }
        if (this.beanMap.containsKey(beanid)){
            return this.beanMap.get(beanid);

        }
        if(bd.isLazy()){
            Object obj=null;
            try {
                obj=Class.forName(bd.getClassInfo()).newInstance();
                this.beanMap.put(beanid,obj);
                return obj;
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return obj;
        }
        return null;
    }
}
