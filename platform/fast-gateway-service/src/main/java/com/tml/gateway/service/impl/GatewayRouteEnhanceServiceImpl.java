package com.tml.gateway.service.impl;

import cn.hutool.core.date.DateUtil;
import com.google.common.base.Stopwatch;
import com.tml.common.api.CommonResult;
import com.tml.gateway.dto.GatewayBlackListDto;
import com.tml.gateway.entity.GatewayBlackList;
import com.tml.gateway.service.*;
import com.tml.gateway.util.GatewayUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Description 网关增强服务接口 实现类
 * @Author TuMingLong
 * @Date 2020/7/28 18:14
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GatewayRouteEnhanceServiceImpl implements IGatewayRouteEnhanceService {

    private final IGatewayBlackListService gatewayBlackListService;
    private final IGatewayBlackListLogService gatewayBlackListLogService;
    private final IGatewayRouteLimitRuleService gatewayRouteLimitRuleService;
    private final IGatewayRouteLimitLogService gatewayRouteLimitLogService;
    private final IGatewayRouteLogService gatewayRouteLogService;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public Mono<Void> filterBackList(ServerWebExchange exchange) {
        Stopwatch stopwatch=Stopwatch.createStarted();
        ServerHttpRequest request=exchange.getRequest();
        ServerHttpResponse response=exchange.getResponse();
        try {
            URI originUri=getGatewayOriginalRequestUri(exchange);
            if(originUri!=null){
                String requestIp= GatewayUtil.getServerHttpRequestIpAddress(request);
                String requestMethod=request.getMethodValue();
                AtomicBoolean forbid=new AtomicBoolean(false);
                GatewayBlackListDto dto=new GatewayBlackListDto();
                dto.setIp(requestIp);
                List<GatewayBlackList> list=gatewayBlackListService.findByCondition(dto);
                checkBlackList(forbid,list,originUri,requestMethod);
                if(forbid.get()){
                    return GatewayUtil.makeWebFluxResponse(response, MediaType.APPLICATION_JSON_VALUE,
                            HttpStatus.NOT_ACCEPTABLE, CommonResult.failed("黑名单限制，禁止访问"));
                }
                log.info("BlackList verification completed: {}",stopwatch.stop());
            }else{
                log.info("BlackList verification completed: no BlackList check - {}",stopwatch.stop());
            }
        }catch (Exception e){
            log.warn("BlackList verification failed: {} - {}",e.getMessage(),stopwatch.stop());
        }
        return null;
    }

    @Override
    public Mono<Void> filterRateLimit(ServerWebExchange exchange) {
        return null;
    }

    @Override
    public void saveBlackListLog(ServerWebExchange exchange) {

    }

    @Override
    public void saveRateLimitLog(ServerWebExchange exchange) {

    }

    @Override
    public void saveRouteLog(ServerWebExchange exchange) {

    }

    private void checkBlackList(AtomicBoolean forbid,List<GatewayBlackList> blackLists,URI uri,String requestMethod){
        blackLists.stream().forEach(gatewayBlackList -> {
            if(pathMatcher.match(gatewayBlackList.getRequestUri(),uri.getPath()) && gatewayBlackList.getStatus()==1){
                if(GatewayBlackList.METHOD_ALL.equalsIgnoreCase(gatewayBlackList.getRequestMethod())
                || StringUtils.equalsIgnoreCase(requestMethod,gatewayBlackList.getRequestMethod())){
                    if(gatewayBlackList.getLimitFrom()!=null && gatewayBlackList.getLimitTo()!=null){
                        LocalDateTime now=LocalDateTime.now();
                        if(now.isAfter(gatewayBlackList.getLimitFrom()) && now.isBefore(gatewayBlackList.getLimitTo())){
                            forbid.set(true);
                        }
                    }else {
                        forbid.set(true);
                    }
                }
            }
        });
    }

    private URI getGatewayOriginalRequestUri(ServerWebExchange exchange) {
        LinkedHashSet<URI> uris = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ORIGINAL_REQUEST_URL_ATTR);
        URI originUri = null;
        if (uris != null) {
            originUri = uris.stream().findFirst().orElse(null);
        }
        return originUri;
    }

    private URI getGatewayRequestUri(ServerWebExchange exchange) {
        return exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR);
    }

    private Route getGatewayRoute(ServerWebExchange exchange) {
        return exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
    }
}
