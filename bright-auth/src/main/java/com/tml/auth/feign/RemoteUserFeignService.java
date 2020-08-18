package com.tml.auth.feign;

import com.tml.api.system.IRemoteUserService;
import com.tml.auth.feign.fallback.RemoteUserFeignServiceFallback;
import com.tml.common.core.entity.constant.BrightServerConstant;
import org.springframework.cloud.openfeign.FeignClient;


/**
 * @author JacksonTu
 * @version 1.0
 * @description 用户服务
 * @since 2020/8/10 22:25
 */
@FeignClient(value = BrightServerConstant.BRIGHT_SERVER_SYSTEM, contextId = "RemoteUserServiceClient",fallbackFactory = RemoteUserFeignServiceFallback.class)
public interface RemoteUserFeignService extends IRemoteUserService {
}
