package com.tml.design.observer;

/**
 * @Description 设计模式 观察者模式
 * 抽象被观察者接口
 * 声明了添加、删除、通知观察者方法
 * @Author TuMingLong
 * @Date 2019/10/16 15:55
 */
public interface Observerable {
    public void registerObserver(Observer o);

    public void removeObserver(Observer o);

    public void notifyObserver();

}
