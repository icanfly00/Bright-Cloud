package com.tml.server.system.controller;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.tml.api.system.IRemoteGatewayService;
import com.tml.api.system.entity.*;
import com.tml.common.core.entity.ResultBody;
import com.tml.server.system.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author JacksonTu
 * @version 1.0
 * @description 网关服务
 * @since 2020/8/13 13:36
 */
@RestController
@RequiredArgsConstructor
public class RemoteGatewayController implements IRemoteGatewayService {

    private final IGatewayBlockListService blockListService;

    private final IGatewayBlockListLogService blockListLogService;

    private final IGatewayRouteLimitRuleService routeLimitRuleService;

    private final IGatewayRouteLimitRuleLogService routeLimitRuleLogService;

    private final IGatewayRouteLogService routeLogService;

    @Override
    public ResultBody<List<GatewayBlockList>> listGatewayBlockList() {
        List<GatewayBlockList> list=blockListService.listGatewayBlockList();
        return ResultBody.ok(list);
    }

    @Override
    public ResultBody<GatewayRouteLimitRule> getGatewayRouteLimitRule(String uri, String method) {
        GatewayRouteLimitRule gatewayRouteLimitRule=routeLimitRuleService.getGatewayRouteLimitRule(uri,method);
        return ResultBody.ok(gatewayRouteLimitRule);
    }

    @Override
    public ResultBody saveGatewayBlockListLog(GatewayBlockListLog gatewayBlockListLog) {
        gatewayBlockListLog.setCreateTime(DateUtil.parse(gatewayBlockListLog.getCreateTimeFrom(),DatePattern.NORM_DATETIME_PATTERN));
        blockListLogService.saveGatewayBlockListLog(gatewayBlockListLog);
        return ResultBody.ok("");
    }

    @Override
    public ResultBody saveGatewayRouteLimitRuleLog(GatewayRouteLimitRuleLog gatewayRouteLimitRuleLog) {
        gatewayRouteLimitRuleLog.setCreateTime(DateUtil.parse(gatewayRouteLimitRuleLog.getCreateTimeFrom(),DatePattern.NORM_DATETIME_PATTERN));
        routeLimitRuleLogService.saveGatewayRouteLimitRuleLog(gatewayRouteLimitRuleLog);
        return ResultBody.ok("");
    }

    @Override
    public ResultBody saveGatewayRouteLog(GatewayRouteLog gatewayRouteLog) {
        gatewayRouteLog.setCreateTime(DateUtil.parse(gatewayRouteLog.getCreateTimeFrom(),DatePattern.NORM_DATETIME_PATTERN));
        routeLogService.saveGatewayRouteLog(gatewayRouteLog);
        return ResultBody.ok("");
    }

    @Override
    public ResultBody<Integer> getCurrentRequestCount(String uri, String ip) {
       int count= routeLimitRuleService.getCurrentRequestCount(uri,ip);
        return ResultBody.ok(count);
    }

    @Override
    public ResultBody setCurrentRequestCount(String uri, String ip, Long time) {
        routeLimitRuleService.setCurrentRequestCount(uri,ip,time);
        return ResultBody.ok("");

    }

    @Override
    public ResultBody incrCurrentRequestCount(String uri, String ip) {
        routeLimitRuleService.incrCurrentRequestCount(uri,ip);
        return ResultBody.ok("");

    }
}
