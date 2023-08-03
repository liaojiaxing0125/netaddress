package com.it.aopproxy;

public class HelloImpl implements Hello{
    @Override
    public void sayHello() {
        System.out.println("hello");
    }

    @Override
    public void showBye() {
        System.out.println("bye");

    }
}
