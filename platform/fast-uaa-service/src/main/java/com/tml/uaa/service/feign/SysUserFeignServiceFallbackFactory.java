package com.tml.uaa.service.feign;

import com.tml.common.api.CommonResult;
import com.tml.system.entity.SysUser;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Set;

/**
 * @Description com.tml.uaa.service.feign
 * @Author TuMingLong
 * @Date 2020/5/20 20:14
 */
@Slf4j
@Configuration
public class SysUserFeignServiceFallbackFactory implements FallbackFactory<SysUserFeignService> {
    @Override
    public SysUserFeignService create(Throwable throwable) {
        return new SysUserFeignService() {
            @Override
            public CommonResult<SysUser> findSecurityUserByUsername(String username) {
                log.error("服务调用失败：" + throwable.getMessage());
                return CommonResult.failed("服务调用失败：" + throwable.getMessage());
            }

            @Override
            public CommonResult<SysUser> findSecurityUserByPhone(String phone) {
                log.error("服务调用失败：" + throwable.getMessage());
                return CommonResult.failed("服务调用失败：" + throwable.getMessage());
            }

            @Override
            public CommonResult<Set<String>> findPermsByUserId(Integer userId) {
                log.error("服务调用失败：" + throwable.getMessage());
                return CommonResult.failed("服务调用失败：" + throwable.getMessage());
            }

            @Override
            public CommonResult<Set<String>> findRoleIdByUserId(Integer userId) {
                log.error("服务调用失败：" + throwable.getMessage());
                return CommonResult.failed("服务调用失败：" + throwable.getMessage());
            }

            @Override
            public CommonResult<String> findDataPermsByUserId(Integer userId) {
                log.error("服务调用失败：" + throwable.getMessage());
                return CommonResult.failed("服务调用失败：" + throwable.getMessage());
            }
        };
    }
}
