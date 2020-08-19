package com.tml.api.system;

import com.tml.api.system.entity.SysUser;
import com.tml.api.system.entity.SysUserConnection;
import com.tml.api.system.entity.SysUserDataPermission;
import com.tml.common.core.entity.ResultBody;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Description 用户服务
 * @Author TuMingLong
 * @Date 2020/8/10 13:49
 */
public interface IRemoteUserService {
    /**
     * 获取用户
     *
     * @param username
     * @return
     */
    @GetMapping("/user/findByName/{username}")
    ResultBody<SysUser> findByName(@PathVariable("username") String username);

    /**
     * 获取用户权限集
     *
     * @param username
     * @return
     */
    @GetMapping("/user/findUserPermissions/{username}")
    ResultBody<String> findUserPermissions(@PathVariable("username") String username);

    /**
     * 获取用户数据权限集
     *
     * @param userId
     * @return
     */
    @GetMapping("/user/findUserDataPermissions/{userId}")
    ResultBody<List<SysUserDataPermission>> findUserDataPermissions(@PathVariable("userId") Long userId);

    /**
     * 注册用户
     *
     * @param username
     * @param password
     * @return
     */
    @GetMapping("/user/registerUser")
    ResultBody<SysUser> registerUser(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password);


    /**
     * 根据条件查询关联关系
     *
     * @param providerName   平台名称
     * @param providerUserId 平台用户ID
     * @return 关联关系
     */
    @GetMapping("/user/findUserConnectionByCondition")
    ResultBody<SysUserConnection> findUserConnectionByCondition(@RequestParam(value = "providerName") String providerName, @RequestParam(value = "providerUserId") String providerUserId);

    /**
     * 根据条件查询关联关系
     *
     * @param username 用户名
     * @return 关联关系
     */
    @GetMapping("/user/findUserConnectionByUsername")
    ResultBody<List<SysUserConnection>> findUserConnectionByUsername(@RequestParam(value = "username") String username);

    /**
     * 新增
     *
     * @param sysUserConnection userConnection
     */
    @PostMapping(value = "/user/createUserConnection", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    ResultBody saveUserConnection(SysUserConnection sysUserConnection);

    /**
     * 删除
     *
     * @param username     username 用户名
     * @param providerName providerName 平台名称
     */
    @GetMapping("/user/deleteUserConnectionByCondition")
    ResultBody deleteUserConnectionByCondition(@RequestParam(value = "username") String username, @RequestParam(value = "providerName") String providerName);
}
