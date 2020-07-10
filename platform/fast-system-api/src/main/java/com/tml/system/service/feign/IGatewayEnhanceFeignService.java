package com.tml.system.service.feign;

import com.sun.deploy.util.BlackList;
import com.tml.common.api.CommonResult;
import com.tml.system.entity.GatewayBlackList;
import com.tml.system.entity.GatewayRouteLimitRule;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description 网关增强服务
 * @Author TuMingLong
 * @Date 2020/7/9 18:06
 */
public interface IGatewayEnhanceFeignService {

    /**
     * 加载所有黑名单信息
     */
    @GetMapping("/gateway/enhance/loadAllBackList")
    CommonResult<Integer>  loadAllBackList();

    /**
     * 加载所有限流规则信息
     */
    @GetMapping("/gateway/enhance/loadAllRouteLimitRule")
    CommonResult<Integer> loadAllRouteLimitRule();


    /**
     * 加载所有所有网关
     */
    @GetMapping("/gateway/enhance/loadAllRoute")
    CommonResult<Integer> loadAllRoute();

    /**
     * 查找黑名单
     * @param ip 请求IP
     * @param requestUri 请求URI
     * @param requestMethod 请求方法
     * @return
     */
    @GetMapping("/gateway/enhance/findGatewayBlackList")
    CommonResult<GatewayBlackList> findGatewayBlackList(@RequestParam("ip") String ip,
                                                        @RequestParam("requestUri") String requestUri,
                                                        @RequestParam("requestMethod") String requestMethod);

    /**
     * 查找限流规则
     * @param requestUri 请求URI
     * @param requestMethod 请求方法
     * @return
     */
    @GetMapping("/gateway/enhance/findGatewayRouteLimitRule")
    CommonResult<GatewayRouteLimitRule> findGatewayRouteLimitRule(@RequestParam("requestUri") String requestUri,
                                                                  @RequestParam("requestMethod") String requestMethod);
}
