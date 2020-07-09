package com.tml.system.listener;

import com.fasterxml.jackson.databind.JsonNode;
import com.tml.common.constant.CacheConstant;
import com.tml.common.constant.QueueConstant;
import com.tml.common.redis.service.RedisService;
import com.tml.common.util.BeanConvertUtil;
import com.tml.common.util.JacksonUtil;
import com.tml.system.entity.SysApi;
import com.tml.system.service.ISysApiService;
import lombok.extern.slf4j.Slf4j;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.Duration;
import java.util.Map;

/**
 * @Description 接收接口消息
 * @Author TuMingLong
 * @Date 2020/7/4 20:11
 */
@Slf4j
@Component
public class ResourceScanHandler {

    @Resource
    private ISysApiService sysApiService;

    @Resource
    private RedisService redisService;

    @RabbitHandler
    @RabbitListener(queues = QueueConstant.QUEUE_SCAN_API_RESOURCE)
    public void process(Message message, Channel channel) throws IOException {
        log.info("receive: " + new String(message.getBody()));
        log.info("线程名:" + Thread.currentThread().getName());
        log.info("线程ID:" + Thread.currentThread().getId());
        try {
            JsonNode jsonNode = JacksonUtil.getJsonNode(new String(message.getBody()));
            String serviceId = jsonNode.get("application").asText();
            String key = CacheConstant.SCAN_API_RESOURCE_KEY_PREFIX + serviceId;
            if (redisService.hasKey(key)) {
                return;
            }
            JsonNode arrNode = jsonNode.get("mapping");
            if (arrNode.isArray()) {
                for (final JsonNode objNode : arrNode) {
                    String path = objNode.get("path").asText();
                    String apiName = objNode.get("apiName").asText();
                    String isAuth = objNode.get("isAuth").asText();
                    String apiCode = objNode.get("apiCode").asText();
                    String requestMethod = objNode.get("requestMethod").asText();
                    String apiDesc = objNode.get("apiDesc").asText();
                    String methodName = objNode.get("methodName").asText();
                    String className = objNode.get("className").asText();
                    String serviceId2 = objNode.get("serviceId").asText();
                    SysApi sysApi = new SysApi();
                    sysApi.setPath(path);
                    sysApi.setApiName(apiName);
                    sysApi.setApiCode(apiCode);
                    sysApi.setIsAuth(Integer.valueOf(isAuth));
                    sysApi.setApiDesc(apiDesc);
                    sysApi.setMethodName(methodName);
                    sysApi.setClassName(className);
                    sysApi.setServiceId(serviceId2);
                    sysApi.setRequestMethod(requestMethod);
                    SysApi save = sysApiService.getApi(sysApi.getApiCode());
                    if (save == null) {
                        sysApi.setIsOpen(0);
                        sysApi.setIsPersist(1);
                        sysApiService.addApi(sysApi);
                    } else {
                        sysApi.setApiId(save.getApiId());
                        sysApiService.updateApi(sysApi);
                    }
                }
            }
            if (arrNode.isArray() && arrNode.size() > 0) {
                redisService.set(key, arrNode.size(), 3 * 60);
            }
        } catch (Exception e) {
            log.error("error:", e);
        }
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
    }
}
