package com.tml.system.controller;

import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.useragent.UserAgentUtil;
import com.tml.common.api.CommonResult;
import com.tml.log.annotation.AutoLog;
import com.tml.common.util.SecurityUtil;
import com.tml.common.util.AddressUtil;
import com.tml.system.entity.SysUser;
import com.tml.system.entity.SysUserLog;
import com.tml.system.service.ISysUserLogService;
import com.tml.system.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

/**
 * @Description 系统用户管理接口
 * @Author TuMingLong
 * @Date 2020/5/20 14:52
 */
@Api(value = "系统用户管理接口",tags = "系统用户管理接口")
@Slf4j
@Validated
@RestController
@RequestMapping("/sys/user")
public class SysUserController {

    @Resource
    private ISysUserService sysUserService;
    @Resource
    private ISysUserLogService sysUserLogService;

    @ApiOperation(value = "根据用户名获取登录用户信息", notes = "根据用户名获取登录用户信息")
    @ApiImplicitParam(paramType = "query", name = "username", value = "用户名", required = true, dataType = "String")
    @GetMapping("/findSecurityUserByUsername")
    public CommonResult<SysUser> findSecurityUserByUsername(@RequestParam("username") String username) {
        SysUser sysUser = new SysUser();
        if (!StringUtils.isEmpty(username)) {
            sysUser.setUsername(username);
        }
        SysUser user = sysUserService.findSecurityUserByUser(sysUser);
        return CommonResult.success(user);
    }

    @ApiOperation(value = "根据用户手机号获取登录用户信息", notes = "根据用户手机号获取登录用户信息")
    @ApiImplicitParam(paramType = "query", name = "phone", value = "手机号", required = true, dataType = "String")
    @GetMapping("/findSecurityUserByPhone")
    public CommonResult<SysUser> findSecurityUserByPhone(@RequestParam("phone") String phone) {
        SysUser sysUser = new SysUser();
        if (!StringUtils.isEmpty(phone)) {
            sysUser.setPhone(phone);
        }
        SysUser user = sysUserService.findSecurityUserByUser(sysUser);
        return CommonResult.success(user);
    }


    /**
     * 根据用户Id查询权限
     *
     * @param userId
     * @return
     */
    @ApiOperation(value = "根据用户Id查询权限", notes = "根据用户Id查询权限")
    @ApiImplicitParam(paramType = "query", name = "userId", value = "用户Id", required = true, dataType = "Integer")
    @GetMapping("/findPermsByUserId")
    public CommonResult<Set<String>> findPermsByUserId(@RequestParam("userId") Integer userId) {
        Set<String> perms = sysUserService.findPermsByUserId(userId);
        return CommonResult.success(perms);
    }

    /**
     * 根据用户Id查询角色集合
     *
     * @param userId
     * @return
     */
    @ApiOperation(value = "根据用户Id查询角色集合", notes = "根据用户Id查询角色集合")
    @ApiImplicitParam(paramType = "query", name = "userId", value = "用户Id", required = true, dataType = "Integer")
    @GetMapping("/findRoleIdByUserId")
    public CommonResult<Set<String>> findRoleIdByUserId(@RequestParam("userId") Integer userId) {
        Set<String> roles = sysUserService.findRoleIdByUserId(userId);
        return CommonResult.success(roles);
    }

    /**
     * 根据用户Id查询数据权限
     *
     * @param userId
     * @return
     */
    @ApiOperation(value = "根据用户Id查询数据权限", notes = "根据用户Id查询数据权限")
    @ApiImplicitParam(paramType = "query", name = "userId", value = "用户Id", required = true, dataType = "Integer")
    @GetMapping("/findDataPermsByUserId")
    public CommonResult<String> findDataPermsByUserId(@RequestParam("userId") Integer userId) {
        String dataPerms = null;
        List<Integer> list = sysUserService.findDataPermsByUserId(userId);
        if (list.isEmpty()) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        boolean flag = false;
        for (Integer integer : list) {
            if (flag) {
                result.append(",");
            } else {
                flag = true;
            }
            result.append(integer);
        }
        dataPerms = result.toString();
        return CommonResult.success(dataPerms);
    }

    /**
     * 记录登录日志
     *
     * @param request
     */
    @ApiOperation(value = "记录登录日志", notes = "记录登录日志")
    @AutoLog(description = "记录登录日志")
    @GetMapping("/success")
    public CommonResult loginSuccess(HttpServletRequest request) {
        String currentUsername = SecurityUtil.getUser().getUsername();
        // update last login time
        this.sysUserService.updateLoginTime(currentUsername);
        // save login log
        SysUserLog sysUserLog = new SysUserLog();
        sysUserLog.setUsername(currentUsername);
        String ip = ServletUtil.getClientIP(request);
        sysUserLog.setIp(ip);
        sysUserLog.setLocation(AddressUtil.getCityInfo(ip));
        String uaStr = request.getHeader("user-agent");
        sysUserLog.setBrowser(UserAgentUtil.parse(uaStr).getBrowser().toString());
        sysUserLog.setOs(UserAgentUtil.parse(uaStr).getOs().toString());
        this.sysUserLogService.saveUserLog(sysUserLog);
        return CommonResult.success(SecurityUtil.getUser());
    }
}
