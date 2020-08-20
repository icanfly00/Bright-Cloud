package com.tml.design.observer;

/**
 * @Description 设计模式 观察者模式
 * 抽象观察者接口
 * 定义了一个update方法，当被观察者调用notifyObservers()方式时，观察者和的update()方法会被回调
 * @Author TuMingLong
 * @Date 2019/10/16 15:58
 */
public interface Observer {

    public void update(String message);
}
