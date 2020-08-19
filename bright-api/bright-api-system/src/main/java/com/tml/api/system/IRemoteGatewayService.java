package com.tml.api.system;

import com.tml.api.system.entity.*;
import com.tml.common.core.entity.ResultBody;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author JacksonTu
 * @version 1.0
 * @description 网关服务
 * @since 2020/8/13 10:10
 */
public interface IRemoteGatewayService {
    /**
     * 获取黑名单集合
     *
     * @param
     * @return
     */
    @GetMapping("/gateway/listGatewayBlockList")
    ResultBody<List<GatewayBlockList>> listGatewayBlockList();

    /**
     * 根据路径和请求方法获取限流规则
     *
     * @param uri
     * @param method
     * @return
     */
    @GetMapping("/gateway/getGatewayRouteLimitRule")
    ResultBody<GatewayRouteLimitRule> getGatewayRouteLimitRule(@RequestParam("uri") String uri, @RequestParam("method") String method);

    /**
     * 保存黑名单日志
     *
     * @param gatewayBlockListLog
     * @return
     */
    @PostMapping(value = "/gateway/saveGatewayBlockListLog", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    ResultBody saveGatewayBlockListLog(GatewayBlockListLog gatewayBlockListLog);

    /**
     * 保存限流日志
     *
     * @param gatewayRouteLimitRuleLog
     * @return
     * @PostMapping(value = "/uploadFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
     */
    @PostMapping(value = "/gateway/saveGatewayRouteLimitRuleLog", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    ResultBody saveGatewayRouteLimitRuleLog(GatewayRouteLimitRuleLog gatewayRouteLimitRuleLog);

    /**
     * 保存网关日志
     *
     * @param gatewayRouteLog
     * @return
     */
    @PostMapping(value = "/gateway/saveGatewayRouteLog", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    ResultBody saveGatewayRouteLog(GatewayRouteLog gatewayRouteLog);

    /**
     * 获取当前请求次数
     *
     * @param uri uri
     * @param ip  ip
     * @return 次数
     */
    @GetMapping("/gateway/getCurrentRequestCount")
    ResultBody<Integer> getCurrentRequestCount(@RequestParam("uri") String uri, @RequestParam("ip") String ip);


    /**
     * 设置请求次数
     *
     * @param uri  uri
     * @param ip   ip
     * @param time time
     */
    @GetMapping("/gateway/setCurrentRequestCount")
    ResultBody setCurrentRequestCount(@RequestParam("uri") String uri, @RequestParam("ip") String ip, @RequestParam("time") Long time);

    /**
     * 递增请求次数
     *
     * @param uri uri
     * @param ip  ip
     */
    @GetMapping("/gateway/incrCurrentRequestCount")
    ResultBody incrCurrentRequestCount(@RequestParam("uri") String uri, @RequestParam("ip") String ip);
}
