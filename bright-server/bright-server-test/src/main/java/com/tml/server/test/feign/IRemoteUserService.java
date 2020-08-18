package com.tml.server.test.feign;

import com.tml.api.system.entity.SysUser;
import com.tml.common.core.entity.CommonResult;
import com.tml.common.core.entity.QueryRequest;
import com.tml.common.core.entity.constant.BrightServerConstant;
import com.tml.server.test.feign.fallback.RemoteUserServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @description
 * @author JacksonTu
 * @since 2020-08-10 20:30
 * @version 1.0
 */
@FeignClient(value = BrightServerConstant.BRIGHT_SERVER_SYSTEM, contextId = "userServiceClient", fallbackFactory = RemoteUserServiceFallback.class)
public interface IRemoteUserService {

    /**
     * remote /user endpoint
     *
     * @param queryRequest queryRequest
     * @param user         user
     * @return CommonResult
     */
    @GetMapping("user")
    CommonResult userList(@RequestParam("queryRequest") QueryRequest queryRequest, @RequestParam("user") SysUser user);
}
