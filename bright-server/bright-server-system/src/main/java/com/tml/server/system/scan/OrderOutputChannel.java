package com.tml.server.system.scan;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @author JacksonTu
 * @version 1.0
 * @description
 * @since 2020/8/20 18:08
 */
public interface OrderOutputChannel {
    //定义通道的名称
    String saveOrder = "saveOrder";
    //定义为输入通道
    @Output(saveOrder)
    MessageChannel saveApi();
}
