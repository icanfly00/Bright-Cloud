package com.tml.server.system.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.tml.api.system.entity.SysUserRole;

import java.util.List;

/**
 * @author JacksonTu
 * @version 1.0
 * @description 用户与角色关联业务层
 * @since 2020-08-10 20:30
 */
public interface ISysUserRoleService extends IService<SysUserRole> {

    /**
     * 删除角色用户管理关系
     *
     * @param roleIds 角色id数组
     */
    void deleteUserRolesByRoleId(String[] roleIds);

    /**
     * 删除角色用户管理关系
     *
     * @param userIds 用户id数组
     */
    void deleteUserRolesByUserId(String[] userIds);

    /**
     * 通过角色id查找对应的用户id
     *
     * @param roleIds 角色id
     * @return 用户id集
     */
    List<String> findUserIdsByRoleId(String[] roleIds);
}
