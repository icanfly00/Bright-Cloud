package com.tml.gateway.service.feign;

import com.tml.common.api.CommonResult;
import com.tml.system.entity.SysUser;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

/**
 * @Description com.tml.gateway.service.feign
 * @Author TuMingLong
 * @Date 2020/7/10 16:03
 */
@Slf4j
@Configuration
public class GatewayEnhanceFeignServiceFallbackFactory implements FallbackFactory<GatewayEnhanceFeignService> {
    @Override
    public GatewayEnhanceFeignService create(Throwable throwable) {
        return null;
    }
}
