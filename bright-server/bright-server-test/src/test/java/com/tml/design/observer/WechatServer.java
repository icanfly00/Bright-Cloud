package com.tml.design.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 设计模式 观察者模式
 * 被观察者
 * @Author TuMingLong
 * @Date 2019/10/16 16:03
 */
public class WechatServer implements Observerable {
    private List<Observer> list;
    private String message;

    public WechatServer() {
        list = new ArrayList<>();
    }

    @Override
    public void registerObserver(Observer o) {
        list.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        if (!list.isEmpty()) {
            list.remove(o);
        }

    }

    @Override
    public void notifyObserver() {
        for (Observer observer : list) {
            observer.update(message);
        }
    }

    public void setInfo(String s) {
        this.message = s;
        System.out.println("微信服务更新消息：" + s);
        //消息更新，通知所有观察者
        notifyObserver();
    }
}
