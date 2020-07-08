package com.tml.common.web.rabbitmq;


import com.tml.common.constant.QueueConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import javax.annotation.Resource;


/**
 * @Description RabbitMQ配置
 * @Author TuMingLong
 * @Date 2020/4/1 23:08
 */
@Slf4j
public class RabbitConfig {

    public RabbitConfig() {
        log.debug("-----RabbitMQ init-----");
    }

    @Resource
    private Environment env;

    @Resource
    private CachingConnectionFactory cachingConnectionFactory;

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(cachingConnectionFactory);
        // 触发setReturnCallback回调必须设置mandatory=true, 否则Exchange没有找到Queue就会丢弃掉消息, 而不会触发回调
        rabbitTemplate.setMandatory(true);
        // 消息是否成功发送到Exchange
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            log.info("消息发送成功:correlationData({}),ack({}),cause({})", correlationData, ack, cause);
        });
        //消息是否从Exchange路由到Queue, 注意: 这是一个失败回调, 只有消息从Exchange路由到Queue失败才会回调这个方法
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            log.info("ReturnCallback:     消息：" + message);
            log.info("ReturnCallback:     回应码：" + replyCode);
            log.info("ReturnCallback:     回应信息：" + replyText);
            log.info("ReturnCallback:     交换机：" + exchange);
            log.info("ReturnCallback:     路由键：" + routingKey);
        });
        return rabbitTemplate;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory(CachingConnectionFactory cachingConnectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(cachingConnectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        //确认消费模式-MANUAL 手动确认
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        factory.setConcurrentConsumers(env.getProperty("spring.rabbitmq.listener.simple.concurrency", int.class));
        factory.setMaxConcurrentConsumers(env.getProperty("spring.rabbitmq.listener.simple.max-concurrency", int.class));
        factory.setPrefetchCount(env.getProperty("spring.rabbitmq.listener.simple.prefetch", int.class));
        return factory;
    }


    @Bean
    public Queue apiResourceQueue() {
        // durable: true持久化
        return new Queue(QueueConstant.QUEUE_SCAN_API_RESOURCE, true);
    }


    //基本交换机
    @Bean
    public TopicExchange apiResourceExchange() {
        return new TopicExchange(QueueConstant.QUEUE_SCAN_API_RESOURCE_EXCHANGE, true, false);
    }

    //创建基本交换机
    @Bean
    public Binding apiResourceExchangeBinding() {
        return BindingBuilder.bind(apiResourceQueue())
                .to(apiResourceExchange()).with(QueueConstant.QUEUE_SCAN_API_RESOURCE_KEY);
    }

}
