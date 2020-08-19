package com.tml.server.test.controller;

import com.tml.api.system.entity.SysUser;
import com.tml.common.core.entity.CommonResult;
import com.tml.common.core.entity.QueryRequest;
import com.tml.common.core.utils.BrightUtil;
import com.tml.server.test.feign.IRemoteUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author JacksonTu
 * @version 1.0
 * @description
 * @since 2020-08-10 20:30
 */
@RestController
@RequestMapping
@RequiredArgsConstructor
public class TestController {

    private final IRemoteUserService remoteUserService;

    /**
     * 用于演示 Feign调用受保护的远程方法
     */
    @GetMapping("user/list")
    public CommonResult getRemoteUserList(QueryRequest request, SysUser user) {
        return remoteUserService.userList(request, user);
    }

    /**
     * 获取当前用户信息
     */
    @GetMapping("user")
    public Map<String, Object> currentUser() {
        Map<String, Object> map = new HashMap<>(5);
        map.put("currentUser", BrightUtil.getCurrentUser());
        map.put("currentUsername", BrightUtil.getCurrentUsername());
        map.put("currentUserAuthority", BrightUtil.getCurrentUserAuthority());
        map.put("currentTokenValue", BrightUtil.getCurrentTokenValue());
        map.put("currentRequestIpAddress", BrightUtil.getHttpServletRequestIpAddress());
        return map;
    }
}
