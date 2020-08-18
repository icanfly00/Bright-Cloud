package com.tml.server.test.feign.fallback;

import com.tml.common.core.annotation.Fallback;
import com.tml.server.test.feign.IRemoteUserService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * @description
 * @author JacksonTu
 * @since 2020-08-10 20:30
 * @version 1.0
 */
@Slf4j
@Fallback
public class RemoteUserServiceFallback implements FallbackFactory<IRemoteUserService> {

    @Override
    public IRemoteUserService create(Throwable throwable) {
        return (queryRequest, user) -> {
            log.error("获取用户信息失败", throwable);
            return null;
        };
    }
}