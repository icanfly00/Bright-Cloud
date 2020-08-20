package com.tml.design.observer;

/**
 * @Description com.tml.design.observer
 * @Author TuMingLong
 * @Date 2019/10/16 16:17
 */
public class ObserverTest {

    public static void main(String[] args) {
        WechatServer server = new WechatServer();
        Observer userZhang = new User("zhang");
        Observer userLi = new User("LiSi");
        Observer userWang = new User("WangWu");
        server.registerObserver(userZhang);
        server.registerObserver(userLi);
        server.registerObserver(userWang);
        server.setInfo("PHP是世界上最好的语言！");
        System.out.println("------------------");
        server.removeObserver(userZhang);
        server.setInfo("JAVA是世界上最好的语言！");
    }
}
