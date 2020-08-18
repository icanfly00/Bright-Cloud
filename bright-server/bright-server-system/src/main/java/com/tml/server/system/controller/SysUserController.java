package com.tml.server.system.controller;

import com.tml.api.system.entity.SysLoginLog;
import com.tml.api.system.entity.SysUser;
import com.tml.common.core.entity.CommonResult;
import com.tml.common.core.entity.QueryRequest;
import com.tml.common.core.entity.constant.StringConstant;
import com.tml.common.core.exception.BrightException;
import com.tml.common.core.utils.BrightUtil;
import com.tml.server.system.annotation.ControllerEndpoint;
import com.tml.server.system.service.ISysLoginLogService;
import com.tml.server.system.service.ISysUserDataPermissionService;
import com.tml.server.system.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description 
 * @author JacksonTu
 * @since 2020-08-10 20:30
 * @version 1.0
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("user")
public class SysUserController {

    private final ISysUserService userService;
    private final ISysUserDataPermissionService userDataPermissionService;
    private final ISysLoginLogService loginLogService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("success")
    public void loginSuccess(HttpServletRequest request) {
        String currentUsername = BrightUtil.getCurrentUsername();
        // update last login time
        this.userService.updateLoginTime(currentUsername);
        // save login log
        SysLoginLog loginLog = new SysLoginLog();
        loginLog.setUsername(currentUsername);
        loginLog.setSystemBrowserInfo(request.getHeader("user-agent"));
        this.loginLogService.saveLoginLog(loginLog);
    }

    @GetMapping("index")
    public CommonResult index() {
        Map<String, Object> data = new HashMap<>(5);
        // 获取系统访问记录
        Long totalVisitCount = loginLogService.findTotalVisitCount();
        data.put("totalVisitCount", totalVisitCount);
        Long todayVisitCount = loginLogService.findTodayVisitCount();
        data.put("todayVisitCount", todayVisitCount);
        Long todayIp = loginLogService.findTodayIp();
        data.put("todayIp", todayIp);
        // 获取近期系统访问记录
        List<Map<String, Object>> lastTenVisitCount = loginLogService.findLastTenDaysVisitCount(null);
        data.put("lastTenVisitCount", lastTenVisitCount);
        SysUser param = new SysUser();
        param.setUsername(BrightUtil.getCurrentUsername());
        List<Map<String, Object>> lastTenUserVisitCount = loginLogService.findLastTenDaysVisitCount(param);
        data.put("lastTenUserVisitCount", lastTenUserVisitCount);
        return new CommonResult().data(data);
    }


    @GetMapping
    @PreAuthorize("hasAuthority('user:view')")
    public CommonResult userList(QueryRequest queryRequest, SysUser user) {
        Map<String, Object> dataTable = BrightUtil.getDataTable(userService.findUserDetailList(user, queryRequest));
        return new CommonResult().data(dataTable);
    }

    @GetMapping("check/{username}")
    public boolean checkUserName(@NotBlank(message = "{required}") @PathVariable String username) {
        return this.userService.findByName(username) == null;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('user:add')")
    @ControllerEndpoint(operation = "新增用户", exceptionMessage = "新增用户失败")
    public void addUser(@Valid SysUser user) {
        this.userService.createUser(user);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('user:update')")
    @ControllerEndpoint(operation = "修改用户", exceptionMessage = "修改用户失败")
    public void updateUser(@Valid SysUser user) {
        this.userService.updateUser(user);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasAuthority('user:update')")
    public CommonResult findUserDataPermissions(@NotBlank(message = "{required}") @PathVariable String userId) {
        String dataPermissions = this.userDataPermissionService.findByUserId(userId);
        return new CommonResult().data(dataPermissions);
    }

    @DeleteMapping("/{userIds}")
    @PreAuthorize("hasAuthority('user:delete')")
    @ControllerEndpoint(operation = "删除用户", exceptionMessage = "删除用户失败")
    public void deleteUsers(@NotBlank(message = "{required}") @PathVariable String userIds) {
        String[] ids = userIds.split(StringConstant.COMMA);
        this.userService.deleteUsers(ids);
    }

    @PutMapping("profile")
    @ControllerEndpoint(exceptionMessage = "修改个人信息失败")
    public void updateProfile(@Valid SysUser user) throws BrightException {
        this.userService.updateProfile(user);
    }

    @PutMapping("avatar")
    @ControllerEndpoint(exceptionMessage = "修改头像失败")
    public void updateAvatar(@NotBlank(message = "{required}") String avatar) {
        this.userService.updateAvatar(avatar);
    }

    @GetMapping("password/check")
    public boolean checkPassword(@NotBlank(message = "{required}") String password) {
        String currentUsername = BrightUtil.getCurrentUsername();
        SysUser user = userService.findByName(currentUsername);
        return user != null && passwordEncoder.matches(password, user.getPassword());
    }

    @PutMapping("password")
    @ControllerEndpoint(exceptionMessage = "修改密码失败")
    public void updatePassword(@NotBlank(message = "{required}") String password) {
        userService.updatePassword(password);
    }

    @PutMapping("password/reset")
    @PreAuthorize("hasAuthority('user:reset')")
    @ControllerEndpoint(exceptionMessage = "重置用户密码失败")
    public void resetPassword(@NotBlank(message = "{required}") String usernames) {
        String[] usernameArr = usernames.split(StringConstant.COMMA);
        this.userService.resetPassword(usernameArr);
    }
}
