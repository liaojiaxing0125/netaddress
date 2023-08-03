package com.it.aop;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OrderBizImpl  implements OrderBiz{
    @Override
    public void makeOrder(int pid, int num) {
        if (num>5){
            throw new RuntimeException("库存数量不足");

        }
        System.out.println("makeOrder的方法调用"+pid+"\t"+num);
    }

    @Override
    public int findOrderId(String name) {
        System.out.println("findOrderId根据商品名:"+name+"\t查找商品对应订单号");
        return 0;
    }

    @Override
    public int findPid(String pname) {
        System.out.println("findPid根据商品名"+pname);
        return (int)(Math.random()*10);
    }

}
