package com.tml.gateway.redis.configure;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tml.gateway.redis.RedisSubService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

/**
 * @author JacksonTu
 * @version 1.0
 * @description redis 订阅配置
 * @since 2020/8/15 10:56
 */
@Configuration
public class BrightRedisPubSubConfigure {
    /**
     * 将订阅器绑定到容器
     * @param connectionFactory
     * @param listener
     * @return
     */
    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                                   MessageListenerAdapter listener) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listener, new PatternTopic("/redis/*"));
        return container;
    }

    /**
     * 消息监听器，使用MessageAdapter可实现自动化解码及方法代理
     *
     * @return
     */
    @Bean
    public MessageListenerAdapter listener(RedisSubService subscriber) {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        mapper.activateDefaultTyping(mapper.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(mapper);

        MessageListenerAdapter adapter = new MessageListenerAdapter(subscriber, "onMessage");
        adapter.setSerializer(jackson2JsonRedisSerializer);
        adapter.afterPropertiesSet();
        return adapter;
    }
}
