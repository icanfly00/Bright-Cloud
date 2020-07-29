package com.tml.gateway.service.impl;

import com.google.common.base.Stopwatch;
import com.tml.common.api.CommonResult;
import com.tml.common.util.AddressUtil;
import com.tml.gateway.dto.GatewayBlackListDto;
import com.tml.gateway.entity.*;
import com.tml.gateway.service.*;
import com.tml.gateway.util.GatewayUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
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

    private static final String METHOD_ALL="All";
    private static final String TOKEN_CHECK_URL="/auth/**";

    private final IGatewayBlackListService gatewayBlackListService;
    private final IGatewayBlackListLogService gatewayBlackListLogService;
    private final IGatewayRouteLimitRuleService gatewayRouteLimitRuleService;
    private final IGatewayRouteLimitLogService gatewayRouteLimitLogService;
    private final IGatewayRouteLogService gatewayRouteLogService;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public Mono<Void> filterBackList(ServerWebExchange exchange) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        try {
            URI originUri = getGatewayOriginalRequestUri(exchange);
            if (originUri != null) {
                String requestIp = GatewayUtil.getServerHttpRequestIpAddress(request);
                String requestMethod = request.getMethodValue();
                AtomicBoolean forbid = new AtomicBoolean(false);
                GatewayBlackListDto dto = new GatewayBlackListDto();
                dto.setIp(requestIp);
                List<GatewayBlackList> list = gatewayBlackListService.findByCondition(dto);
                checkBlackList(forbid, list, originUri, requestMethod);
                if (forbid.get()) {
                    return GatewayUtil.makeWebFluxResponse(response, MediaType.APPLICATION_JSON_VALUE,
                            HttpStatus.NOT_ACCEPTABLE, CommonResult.failed("黑名单限制，禁止访问"));
                }
                log.info("BlackList verification completed: {}", stopwatch.stop());
            } else {
                log.info("BlackList verification completed: no BlackList filter - {}", stopwatch.stop());
            }
        } catch (Exception e) {
            log.warn("BlackList verification failed: {} - {}", e.getMessage(), stopwatch.stop());
        }
        return null;
    }

    @Override
    public Mono<Void> filterRateLimit(ServerWebExchange exchange) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        try {
            URI originUri = getGatewayOriginalRequestUri(exchange);
            if (originUri != null) {
                String requestIp = GatewayUtil.getServerHttpRequestIpAddress(request);
                String requestMethod = request.getMethodValue();
                AtomicBoolean limit = new AtomicBoolean(false);

               GatewayRouteLimitRule routeLimitRule= gatewayRouteLimitRuleService.getGatewayRouteLimitRuleCache(originUri.getPath(),METHOD_ALL);
               if(routeLimitRule==null){
                   routeLimitRule=gatewayRouteLimitRuleService.getGatewayRouteLimitRuleCache(originUri.getPath(),requestMethod);
               }
               if(routeLimitRule!=null){
                   Mono<Void> result=checkRouteLimitRule(limit,routeLimitRule,originUri,requestIp,requestMethod,response);
                   log.info("RouteLimitRule verification completed: {}", stopwatch.stop());
                   if(result!=null){
                       return result;
                   }
               }
            }else {
                log.info("RouteLimitRule verification completed: no rate limit filter - {}", stopwatch.stop());
            }

        } catch (Exception e) {
            log.warn("RouteLimitRule verification failed: {} - {}", e.getMessage(), stopwatch.stop());
        }

        return null;
    }

    @Override
    public void saveBlackListLog(ServerWebExchange exchange) {
        URI originUri = getGatewayOriginalRequestUri(exchange);
        ServerHttpRequest request = exchange.getRequest();
        String requestIp = GatewayUtil.getServerHttpRequestIpAddress(request);
        if(originUri!=null){
            GatewayBlackListLog blackListLog=new GatewayBlackListLog();
            blackListLog.setIp(requestIp);
            blackListLog.setRequestMethod(request.getMethodValue());
            blackListLog.setRequestUri(originUri.getPath());
            blackListLog.setCreateTime(LocalDateTime.now());
            gatewayBlackListLogService.save(blackListLog);
        }
    }

    @Override
    public void saveRateLimitLog(ServerWebExchange exchange) {
        URI originUri = getGatewayOriginalRequestUri(exchange);
        ServerHttpRequest request = exchange.getRequest();
        String requestIp = GatewayUtil.getServerHttpRequestIpAddress(request);
        if(originUri!=null){
            GatewayRouteLimitLog routeLimitLog=new GatewayRouteLimitLog();
            routeLimitLog.setIp(requestIp);
            routeLimitLog.setRequestMethod(request.getMethodValue());
            routeLimitLog.setRequestUri(originUri.getPath());
            routeLimitLog.setCreateTime(LocalDateTime.now());
            gatewayRouteLimitLogService.save(routeLimitLog);
        }
    }

    @Override
    public void saveRouteLog(ServerWebExchange exchange) {
        URI originUri = getGatewayOriginalRequestUri(exchange);
        if(!StringUtils.equalsIgnoreCase(TOKEN_CHECK_URL,originUri.getPath())){
            URI uri=getGatewayRequestUri(exchange);
            Route route=getGatewayRoute(exchange);
            ServerHttpRequest request=exchange.getRequest();
            String ipAddress=GatewayUtil.getServerHttpRequestIpAddress(request);
            if(uri!=null && route!=null){
                GatewayRouteLog routeLog=new GatewayRouteLog();
                routeLog.setIp(ipAddress);
                routeLog.setRequestUri(originUri.getPath());
                routeLog.setRequestMethod(request.getMethodValue());
                routeLog.setTargetServer(route.getId());
                routeLog.setTargetUri(uri.getPath());
                routeLog.setLocation(AddressUtil.getCityInfo(ipAddress));
                gatewayRouteLogService.save(routeLog);
            }
        }
    }

    @Override
    public void loadAllBlackListCache() {
        gatewayBlackListService.saveAllGatewayBlackListCache();
    }

    @Override
    public void loadAllRouteLimitRuleCache() {
        gatewayRouteLimitRuleService.saveAllGatewayRouteLimitRuleCache();
    }

    private void checkBlackList(AtomicBoolean forbid, List<GatewayBlackList> blackLists, URI uri, String requestMethod) {
        blackLists.stream().forEach(gatewayBlackList -> {
            if (pathMatcher.match(gatewayBlackList.getRequestUri(), uri.getPath()) && GatewayBlackList.OPEN.equals(gatewayBlackList.getStatus())) {
                if (GatewayBlackList.METHOD_ALL.equalsIgnoreCase(gatewayBlackList.getRequestMethod())
                        || StringUtils.equalsIgnoreCase(requestMethod, gatewayBlackList.getRequestMethod())) {
                    if (gatewayBlackList.getLimitFrom() != null && gatewayBlackList.getLimitTo() != null) {
                        LocalDateTime now = LocalDateTime.now();
                        if (now.isAfter(gatewayBlackList.getLimitFrom()) && now.isBefore(gatewayBlackList.getLimitTo())) {
                            forbid.set(true);
                        }
                    } else {
                        forbid.set(true);
                    }
                }
            }
        });
    }

    private Mono<Void> checkRouteLimitRule(AtomicBoolean limit,GatewayRouteLimitRule routeLimitRule,
                                           URI uri,String requestIp,String requestMethod,ServerHttpResponse response){
        boolean flag=GatewayRouteLimitRule.OPEN.equals(routeLimitRule.getStatus())
                && (GatewayRouteLimitRule.METHOD_ALL.equalsIgnoreCase(routeLimitRule.getRequestMethod()))
                || StringUtils.equalsIgnoreCase(requestMethod,routeLimitRule.getRequestMethod());
        if(flag) {
            if (routeLimitRule.getLimitFrom() != null && routeLimitRule.getLimitTo() != null) {
                LocalDateTime now = LocalDateTime.now();
                if (now.isAfter(routeLimitRule.getLimitFrom()) && now.isBefore(routeLimitRule.getLimitTo())) {
                    limit.set(true);
                }
            }else {
                limit.set(true);
            }
        }
        if(limit.get()){
            String requestUri=uri.getPath();
            int count=gatewayRouteLimitRuleService.getCurrentRequestCount(requestUri,requestIp);
            if(count==0){
                gatewayRouteLimitRuleService.saveCurrentRequestCount(requestUri,requestIp,routeLimitRule.getIntervalSec());
            }else if(count>routeLimitRule.getCount()){
                return GatewayUtil.makeWebFluxResponse(response, MediaType.APPLICATION_JSON_VALUE,
                        HttpStatus.TOO_MANY_REQUESTS, CommonResult.failed("访问频率超限，请稍后再试"));
            }else {
                gatewayRouteLimitRuleService.incrCurrentRequestCount(requestUri,requestIp);
            }
        }
        return null;
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
