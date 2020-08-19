package com.tml.server.system.controller;

import com.tml.api.system.IRemoteUserService;
import com.tml.api.system.entity.SysUser;
import com.tml.api.system.entity.SysUserConnection;
import com.tml.api.system.entity.SysUserDataPermission;
import com.tml.api.system.entity.SysUserRole;
import com.tml.common.core.entity.ResultBody;
import com.tml.common.core.entity.constant.BrightConstant;
import com.tml.server.system.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @author JacksonTu
 * @version 1.0
 * @description 用户服务接口
 * @since 2020/8/10 21:56
 */
@RestController
@RequiredArgsConstructor
public class RemoteUserController implements IRemoteUserService {

    private final ISysUserService userService;

    private final ISysUserRoleService userRoleService;

    private final ISysMenuService menuService;

    private final ISysUserDataPermissionService userDataPermissionService;

    private final ISysUserConnectionService userConnectionService;

    @Override
    public ResultBody<SysUser> findByName(String username) {
        SysUser user = userService.findSecurityUserByName(username);
        return ResultBody.ok(user);
    }

    @Override
    public ResultBody<String> findUserPermissions(String username) {
        String userPermissions = menuService.findUserPermissions(username);
        return ResultBody.ok(userPermissions);
    }

    @Override
    public ResultBody<List<SysUserDataPermission>> findUserDataPermissions(Long userId) {
        List<SysUserDataPermission> list = userDataPermissionService.findUserDataPermissions(userId);
        return ResultBody.ok(list);
    }

    @Override
    public ResultBody<SysUser> registerUser(String username, String password) {
        SysUser sysUser = new SysUser();
        sysUser.setUsername(username);
        sysUser.setPassword(password);
        sysUser.setCreateTime(new Date());
        sysUser.setStatus(sysUser.STATUS_VALID);
        sysUser.setSex(sysUser.SEX_UNKNOW);
        sysUser.setAvatar(sysUser.DEFAULT_AVATAR);
        sysUser.setDescription("注册用户");
        this.userService.save(sysUser);

        SysUserRole userRole = new SysUserRole();
        userRole.setUserId(sysUser.getUserId());
        // 注册用户角色 ID
        userRole.setRoleId(BrightConstant.REGISTER_ROLE_ID);
        this.userRoleService.save(userRole);
        return ResultBody.ok(sysUser);
    }

    @Override
    public ResultBody<SysUserConnection> findUserConnectionByCondition(String providerName, String providerUserId) {
        SysUserConnection userConnection = userConnectionService.selectByCondition(providerName, providerUserId);
        return ResultBody.ok(userConnection);
    }

    @Override
    public ResultBody<List<SysUserConnection>> findUserConnectionByUsername(String username) {
        List<SysUserConnection> list = userConnectionService.selectByCondition(username);
        return ResultBody.ok(list);
    }

    @Override
    public ResultBody saveUserConnection(SysUserConnection sysUserConnection) {
        userConnectionService.createUserConnection(sysUserConnection);
        return ResultBody.ok("");
    }

    @Override
    public ResultBody deleteUserConnectionByCondition(String username, String providerName) {
        userConnectionService.deleteByCondition(username, providerName);
        return ResultBody.ok("");
    }
}
