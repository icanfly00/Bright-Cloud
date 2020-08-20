package com.tml.design.singleton;

/**
 * @Description 单例模式 饿汉模式
 * @Author TuMingLong
 * @Date 2020/2/25 17:52
 */
public class MySingleton2 {
    private static MySingleton2 instance = new MySingleton2();

    private MySingleton2() {
        System.out.println(Thread.currentThread().getName() + "\t 我是构造方法MySingleton2");
    }

    public static MySingleton2 getInstance() {
        return instance;
    }


    public static void main(String[] args) {
        System.out.println(MySingleton2.getInstance() == MySingleton2.getInstance());
        System.out.println(MySingleton2.getInstance() == MySingleton2.getInstance());
        System.out.println(MySingleton2.getInstance() == MySingleton2.getInstance());
    }
}
