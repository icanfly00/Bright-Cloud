package com.tml.system.service;

import com.tml.common.web.service.IBaseService;
import com.tml.system.entity.SysUser;

import java.util.List;
import java.util.Set;


/**
 * @Description 用户表 服务类
 * @Author TuMingLong
 * @Date 2020/3/31 11:48
 */
public interface ISysUserService extends IBaseService<SysUser> {

    /**
     * 根据用户id查询权限
     *
     * @param userId
     * @return
     */
    Set<String> findPermsByUserId(Integer userId);

    /**
     * 通过用户id查询角色集合
     *
     * @param userId
     * @return
     */
    Set<String> findRoleIdByUserId(Integer userId);

    /**
     *通过用户ID查询数据权限
     * @param userId
     * @return
     */
    List<Integer> findDataPermsByUserId(Integer userId);

    /**
     * 根据用户名查找用户
     *
     * @param username
     * @return
     */
    SysUser findByUsername(String username);

    /**
     * 校验用户是否有效
     *
     * @param sysUser
     */
    boolean checkUser(SysUser sysUser);

    /**
     * 通过用户去查找用户(id/用户名/手机号)
     *
     * @param sysUser
     * @return
     */
    SysUser findSecurityUserByUser(SysUser sysUser);

    /**
     * 更新登录时间
     *
     * @param username
     */
    void updateLoginTime(String username);

}
