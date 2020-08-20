package com.tml.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description 服务端启动
 * @Author TuMingLong
 * @Date 2020/7/22 11:05
 */
public class MyServerMain {
    private  static final Logger logger = LoggerFactory.getLogger(MyClientHandler.class);

    private static MyServer server =new MyServer(55550);
    private static MySensorServer sensorServer =new MySensorServer(9005);

    public static void main(String[] args) {
        logger.info("服务端启动.......");
        //server.start();
        sensorServer.start();
    }
}
