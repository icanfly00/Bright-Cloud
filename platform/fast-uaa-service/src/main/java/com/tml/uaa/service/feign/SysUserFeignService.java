package com.tml.uaa.service.feign;

import com.tml.common.api.CommonResult;
import com.tml.common.constant.ServiceConstant;
import com.tml.system.entity.SysUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

/**
 * @Description com.tml.common.security.service.feign
 * @Author TuMingLong
 * @Date 2020/5/20 15:43
 */
@FeignClient(name = ServiceConstant.SYSTEM_SERVER,
        fallbackFactory = SysUserFeignServiceFallbackFactory.class)
public interface SysUserFeignService {

    @GetMapping(value = "/sys/user/findSecurityUserByUsername")
    CommonResult<SysUser> findSecurityUserByUsername(@RequestParam("username") String username);

    @GetMapping(value = "/sys/user/findSecurityUserByPhone")
    CommonResult<SysUser> findSecurityUserByPhone(@RequestParam("phone") String phone);


    /**
     * 根据用户id查询权限
     *
     * @param userId
     * @return
     */
    @GetMapping(value = "/sys/user/findPermsByUserId")
    CommonResult<Set<String>> findPermsByUserId(@RequestParam("userId") Integer userId);

    /**
     * 通过用户id查询角色集合
     *
     * @param userId
     * @return
     */
    @GetMapping(value = "/sys/user/findRoleIdByUserId")
    CommonResult<Set<String>> findRoleIdByUserId(@RequestParam("userId") Integer userId);

    /**
     * 通过用户id查询数据权限
     *
     * @param userId
     * @return
     */
    @GetMapping(value = "/sys/user/findDataPermsByUserId")
    CommonResult<String> findDataPermsByUserId(@RequestParam("userId") Integer userId);
}
