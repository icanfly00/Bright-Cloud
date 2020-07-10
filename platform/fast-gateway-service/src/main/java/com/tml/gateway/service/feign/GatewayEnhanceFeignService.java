package com.tml.gateway.service.feign;

import com.tml.common.constant.ServiceConstant;
import com.tml.system.service.feign.IGatewayEnhanceFeignService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Description com.tml.gateway.service.feign
 * @Author TuMingLong
 * @Date 2020/7/10 16:01
 */
@FeignClient(name = ServiceConstant.SYSTEM_SERVER,
        fallbackFactory = GatewayEnhanceFeignServiceFallbackFactory.class)
public interface GatewayEnhanceFeignService extends IGatewayEnhanceFeignService {
}
