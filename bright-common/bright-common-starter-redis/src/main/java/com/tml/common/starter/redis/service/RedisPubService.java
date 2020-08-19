package com.tml.common.starter.redis.service;

import com.tml.common.starter.redis.entity.RedisSimpleMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;

import java.util.Date;

/**
 * @author JacksonTu
 * @version 1.0
 * @description redis消息发布服务
 * @since 2020/8/15 10:51
 */
public class RedisPubService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 推送消息
     *
     * @param topicName 主题
     * @param publisher 发布者
     * @param content   内容
     * @param status    状态
     */
    public void publish(String topicName, String publisher, String content, int status) {
        RedisSimpleMessage pushMsg = new RedisSimpleMessage();
        pushMsg.setPublisher(publisher);
        pushMsg.setContent(content);
        pushMsg.setStatus(status);
        pushMsg.setCreateTime(new Date());
        ChannelTopic topic = new ChannelTopic(topicName);
        redisTemplate.convertAndSend(topic.getTopic(), pushMsg);
    }
}
