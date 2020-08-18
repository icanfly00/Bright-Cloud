package com.tml.gateway.feign;

import com.tml.api.system.IRemoteGatewayService;
import com.tml.common.core.entity.constant.BrightServerConstant;
import com.tml.gateway.feign.fallback.RemoteGatewayFeignServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author JacksonTu
 * @version 1.0
 * @description com.tml.gateway.feign
 * @since 2020/8/13 15:09
 */
@FeignClient(value = BrightServerConstant.BRIGHT_SERVER_SYSTEM, contextId = "RemoteGatewayServiceClient",fallbackFactory = RemoteGatewayFeignServiceFallback.class)
public interface RemoteGatewayFeignService extends IRemoteGatewayService {
}
