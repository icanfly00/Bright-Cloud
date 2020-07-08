package com.tml.uaa.controller;

import com.tml.common.api.CommonResult;
import com.tml.common.util.SecurityUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @Description com.tml.uaa.controller
 * @Author TuMingLong
 * @Date 2020/5/21 9:34
 */
@RestController
public class LoginController {


    /**
     * 获取用户基础信息
     *
     * @return
     */
    @ApiOperation(value = "获取当前登录用户信息", notes = "获取当前登录用户信息")
    @GetMapping("/current/user")
    public CommonResult getUserProfile() {

        return CommonResult.success(SecurityUtil.getUser());
    }

    /**
     * 获取当前登录用户信息-SSO单点登录
     *
     * @param principal
     * @return
     */
    @ApiOperation(value = "获取当前登录用户信息-SSO单点登录", notes = "获取当前登录用户信息-SSO单点登录")
    @GetMapping("/current/user/sso")
    public Principal principal(Principal principal) {
        return principal;
    }

}
