package com.it.aop.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Aspect//表明切面类
public class MyAspects {
    @Pointcut("execution(* com.it.aop.*.make*(..))")
    private void a(){}
    private Map<String,Long> map2=new ConcurrentHashMap<>();

    @Before("a()")
    public void recordTime(){
        Date d=new Date();
        System.out.println("下单时间"+d);

    }
    @AfterReturning("a()")
    public void recordParams(JoinPoint jp){
        System.out.println("增强方法为"+jp.getSignature());
        System.out.println("增强目标类为:"+jp.getTarget());
        System.out.println("参数");
        Object[] params=jp.getArgs();
        for (Object o:params){
            System.out.println(o);
        }
    }
    @Pointcut("execution(int com.it.aop.*.findPid(String))")
    private void c(){}
    @AfterReturning(pointcut = "c()",returning = "retValue")
    public void recordPnameCount2(JoinPoint jp,int retValue){
        Object []objs=jp.getArgs();
        String pname= (String) objs[0];
        Long num=1L;
        if (map2.containsKey(pname)){
            num=map2.get(pname);
            num++;

        }
        map2.put(pname+":"+retValue,num);
        System.out.println("统计结果"+map2);


    }
    //////////////////////////////////////对异常处理
    @AfterThrowing(pointcut = "a()",throwing = "ex")
    public void recordException(JoinPoint jp,RuntimeException ex){
        System.out.println("***********异常了**********");
        System.out.println(ex.getMessage());
        System.out.println(jp.getArgs()[0]+"\t"+jp.getArgs()[1]);
        System.out.println("*******************");

    }
    @Pointcut("execution(* com.it.aop.*.find*(..))")
    private void d(){}

    public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {//pjp 就是被调用的find*()
        long start =System.currentTimeMillis();
        Object retval=pjp.proceed();
        long end=System.currentTimeMillis();
        System.out.println("方法执行的时间为"+(end-start));
        return retval;



    }
}
