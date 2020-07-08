package com.tml.system.controller;

import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.useragent.UserAgentUtil;
import com.tml.common.api.CommonResult;
import com.tml.common.log.annotation.AutoLog;
import com.tml.common.util.SecurityUtil;
import com.tml.common.util.AddressUtil;
import com.tml.system.entity.SysUser;
import com.tml.system.entity.SysUserLog;
import com.tml.system.service.ISysUserLogService;
import com.tml.system.service.ISysUserService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

/**
 * @Description com.tml.system.controller
 * @Author TuMingLong
 * @Date 2020/5/20 14:52
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController {

    @Resource
    private ISysUserService sysUserService;
    @Resource
    private ISysUserLogService sysUserLogService;

    @GetMapping("/findSecurityUserByUsername")
    public CommonResult<SysUser> findSecurityUserByUsername(@RequestParam("username") String username) {
        SysUser sysUser = new SysUser();
        if (!StringUtils.isEmpty(username)) {
            sysUser.setUsername(username);
        }
        SysUser user = sysUserService.findSecurityUserByUser(sysUser);
        return CommonResult.success(user);
    }

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
     * 根据用户id查询权限
     *
     * @param userId
     * @return
     */
    @GetMapping("/findPermsByUserId")
    public CommonResult<Set<String>> findPermsByUserId(@RequestParam("userId") Integer userId) {
        Set<String> perms = sysUserService.findPermsByUserId(userId);
        return CommonResult.success(perms);
    }

    /**
     * 通过用户id查询角色集合
     *
     * @param userId
     * @return
     */
    @GetMapping("/findRoleIdByUserId")
    public CommonResult<Set<String>> findRoleIdByUserId(@RequestParam("userId") Integer userId) {
        Set<String> roles = sysUserService.findRoleIdByUserId(userId);
        return CommonResult.success(roles);
    }

    /**
     * 通过用户id查询数据权限
     *
     * @param userId
     * @return
     */
    @GetMapping("/findDataPermsByUserId")
    public CommonResult<String> findDataPermsByUserId(@RequestParam("userId") Integer userId) {
        String dataPerms=null;
        List<Integer> list = sysUserService.findDataPermsByUserId(userId);
        if(list.isEmpty()){
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
        dataPerms=result.toString();
        return CommonResult.success(dataPerms);
    }

    /**
     * 记录登录日志
     *
     * @param request
     */
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
