package com.tml.system.controller;

import com.tml.common.api.CommonResult;
import com.tml.common.constant.CacheConstant;
import com.tml.common.redis.service.RedisService;
import com.tml.system.dto.GatewayBlackListDto;
import com.tml.system.dto.GatewayRouteLimitRuleDto;
import com.tml.system.entity.GatewayBlackList;
import com.tml.system.entity.GatewayRouteLimitRule;
import com.tml.system.service.IGatewayBlackListService;
import com.tml.system.service.IGatewayRouteLimitRuleService;
import com.tml.system.service.IGatewayRouteService;
import com.tml.system.service.feign.IGatewayEnhanceFeignService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.*;

/**
 * @Description 网关增强服务接口
 * @Author TuMingLong
 * @Date 2020/7/10 15:37
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
public class GatewayEnhanceController implements IGatewayEnhanceFeignService {

    private final RedisService redisService;

    private final IGatewayRouteService gatewayRouteService;

    private final IGatewayBlackListService gatewayBlackListService;

    private final IGatewayRouteLimitRuleService gatewayRouteLimitRuleService;

    private final ExecutorService threadPool = new ThreadPoolExecutor(
            Runtime.getRuntime().availableProcessors(),
            new Double(Runtime.getRuntime().availableProcessors() / (1 - 0.9)).intValue(),
            1l,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(Runtime.getRuntime().availableProcessors()),
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.CallerRunsPolicy()
    );


    @Override
    public CommonResult<Integer> loadAllBackList() {
        List<GatewayBlackList> list=gatewayBlackListService.findAllBackList();
        final CountDownLatch latch=new CountDownLatch(list.size());
        if(!list.isEmpty()){
            try{
                list.stream().forEach(gatewayBlackList -> {
                    threadPool.execute(() ->{
                        try {
                            String key= CacheConstant.GATEWAY_BLACK_LIST_CACHE+":"
                                    +gatewayBlackList.getIp()+":"
                                    +gatewayBlackList.getRequestUri()+":"
                                    +gatewayBlackList.getRequestMethod();
                            redisService.set(key,gatewayBlackList);
                        }finally {
                            latch.countDown();
                        }
                    });
                });
            }catch (Exception e) {
                e.printStackTrace();
            }finally {
                try {
                    //TODO: 等待所有线程执行完毕
                    latch.await();
                    //TODO: 关闭线程池
                    threadPool.shutdown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return CommonResult.success(list.size());
        }
        return CommonResult.success(0);
    }

    @Override
    public CommonResult<Integer> loadAllRouteLimitRule() {
        List<GatewayRouteLimitRule> list=gatewayRouteLimitRuleService.findAllRouteLimitRule();
        final CountDownLatch latch=new CountDownLatch(list.size());
        if(!list.isEmpty()) {
            try {
                list.stream().forEach(routeLimitRule -> {
                    threadPool.execute(() -> {
                        try {
                            String key = CacheConstant.GATEWAY_ROUTE_LIMIT_RULE_CACHE + ":"
                                    + routeLimitRule.getRequestUri() + ":"
                                    + routeLimitRule.getRequestMethod();
                            redisService.set(key, routeLimitRule);
                        } finally {
                            latch.countDown();
                        }
                    });
                });
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    //TODO: 等待所有线程执行完毕
                    latch.await();
                    //TODO: 关闭线程池
                    threadPool.shutdown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return CommonResult.success(list.size());
        }
        return CommonResult.success(0);
    }

    @Override
    public CommonResult<Integer> loadAllRoute() {
        return null;
    }

    @Override
    public CommonResult<GatewayBlackList> findGatewayBlackList(String ip, String requestUri, String requestMethod) {
        String key= CacheConstant.GATEWAY_BLACK_LIST_CACHE+":"
                +ip+":"
                +requestUri+":"
                +requestMethod;
        if(redisService.hasKey(key)){
            return CommonResult.success((GatewayBlackList)redisService.get(key));
        }
        GatewayBlackListDto dto=new GatewayBlackListDto();
        dto.setIp(ip);
        dto.setRequestUri(requestUri);
        dto.setRequestMethod(requestMethod);
        dto.setStatus("1");
        return CommonResult.success(gatewayBlackListService.findByCondition(dto));
    }

    @Override
    public CommonResult<GatewayRouteLimitRule> findGatewayRouteLimitRule(String requestUri, String requestMethod) {
        String key = CacheConstant.GATEWAY_ROUTE_LIMIT_RULE_CACHE + ":"
                + requestUri + ":"
                + requestMethod;
        if(redisService.hasKey(key)){
            return CommonResult.success((GatewayRouteLimitRule)redisService.get(key));
        }
        GatewayRouteLimitRuleDto dto=new GatewayRouteLimitRuleDto();
        dto.setRequestUri(requestUri);
        dto.setRequestMethod(requestMethod);
        dto.setStatus("1");
        return CommonResult.success(gatewayRouteLimitRuleService.findByCondition(dto));
    }
}
