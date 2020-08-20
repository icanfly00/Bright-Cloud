package com.tml.design.singleton;

/**
 * @Description 单例模式 懒汉式单例 Double Check Locking 双检查锁机制
 * @Author TuMingLong
 * @Date 2019/8/29 14:21
 */
public class MySingleton {

    //使用volatile关键字保证其可见性和禁止指令重排
    private static volatile MySingleton instance = null;

    private MySingleton() {
        System.out.println(Thread.currentThread().getName() + "\t 我是构造方法MySingleton");
    }

    public static MySingleton getInstance() {
        if (null == instance) {
            synchronized (MySingleton.class) {
                if (null == instance) {
                    instance = new MySingleton();
                }
            }
        }
        return instance;
    }

    // 多线程环境下，单例
    public static void main(String[] args) {
        for (int i = 1; i <= 10; i++) {
            new Thread(() -> {
                MySingleton.getInstance();
            }, String.valueOf(i)).start();
        }
    }
}
