package com.tml.design.observer;

/**
 * @Description 设计模式 观察者模式
 * @Author 观察者 实现了update方法
 * @Date 2019/10/16 16:10
 */
public class User implements Observer {

    private String name;

    private String message;

    public User(String name) {
        this.name = name;
    }

    @Override
    public void update(String message) {
        this.message = message;
        read();
    }

    public void read() {
        System.out.println(name + " 收到推送消息：" + message);
    }
}
