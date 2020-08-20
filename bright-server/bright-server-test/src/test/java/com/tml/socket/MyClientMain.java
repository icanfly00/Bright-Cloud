package com.tml.socket;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description 客户端启动类
 * @Author TuMingLong
 * @Date 2020/7/22 11:07
 */
public class MyClientMain {
    private  static final Logger logger = LoggerFactory.getLogger(MyClientHandler.class);

    private  static MyClient client = new MyClient("127.0.0.1", 55550);

    public static void main(String[] args) {
        logger.info("客户端启动.......");
        client.connect();
    }
}
