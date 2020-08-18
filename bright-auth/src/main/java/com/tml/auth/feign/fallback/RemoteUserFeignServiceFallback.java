package com.tml.auth.feign.fallback;

import com.tml.api.system.entity.SysUser;
import com.tml.api.system.entity.SysUserConnection;
import com.tml.api.system.entity.SysUserDataPermission;
import com.tml.auth.feign.RemoteUserFeignService;
import com.tml.common.core.entity.ResultBody;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author JacksonTu
 * @version 1.0
 * @description
 * @since 2020/8/10 22:29
 */
@Slf4j
@Component
public class RemoteUserFeignServiceFallback implements FallbackFactory<RemoteUserFeignService> {
    @Override
    public RemoteUserFeignService create(Throwable throwable) {
        return new RemoteUserFeignService() {
            @Override
            public ResultBody<SysUser> findByName(String username) {
                log.error("服务调用失败：" + throwable.getMessage());
                return ResultBody.failed("服务调用失败：" + throwable.getMessage());
            }

            @Override
            public ResultBody<String> findUserPermissions(String username) {
                log.error("服务调用失败：" + throwable.getMessage());
                return ResultBody.failed("服务调用失败：" + throwable.getMessage());
            }

            @Override
            public ResultBody<List<SysUserDataPermission>> findUserDataPermissions(Long userId) {
                log.error("服务调用失败：" + throwable.getMessage());
                return ResultBody.failed("服务调用失败：" + throwable.getMessage());
            }

            @Override
            public ResultBody<SysUser> registerUser(String username, String password) {
                log.error("服务调用失败：" + throwable.getMessage());
                return ResultBody.failed("服务调用失败：" + throwable.getMessage());
            }

            @Override
            public ResultBody<SysUserConnection> findUserConnectionByCondition(String providerName, String providerUserId) {
                log.error("服务调用失败：" + throwable.getMessage());
                return ResultBody.failed("服务调用失败：" + throwable.getMessage());
            }

            @Override
            public ResultBody<List<SysUserConnection>> findUserConnectionByUsername(String username) {
                log.error("服务调用失败：" + throwable.getMessage());
                return ResultBody.failed("服务调用失败：" + throwable.getMessage());
            }

            @Override
            public ResultBody saveUserConnection(SysUserConnection sysUserConnection) {
                log.error("服务调用失败：" + throwable.getMessage());
                return ResultBody.failed("服务调用失败：" + throwable.getMessage());
            }

            @Override
            public ResultBody deleteUserConnectionByCondition(String username, String providerName) {
                log.error("服务调用失败：" + throwable.getMessage());
                return ResultBody.failed("服务调用失败：" + throwable.getMessage());
            }
        };
    }
}
