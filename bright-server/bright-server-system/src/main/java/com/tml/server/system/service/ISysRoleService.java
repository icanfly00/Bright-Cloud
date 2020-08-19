package com.tml.server.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tml.api.system.entity.SysRole;
import com.tml.common.core.entity.QueryRequest;

import java.util.List;

/**
 * @author JacksonTu
 * @version 1.0
 * @description 角色信息业务层
 * @since 2020-08-10 20:30
 */
public interface ISysRoleService extends IService<SysRole> {

    /**
     * 查找角色分页数据
     *
     * @param role    role
     * @param request request
     * @return 角色分页数据
     */
    IPage<SysRole> pageRole(SysRole role, QueryRequest request);

    /**
     * 获取用户角色
     *
     * @param username 用户名
     * @return 角色集
     */
    List<SysRole> findUserRole(String username);

    /**
     * 获取所有角色
     *
     * @return 角色列表
     */
    List<SysRole> findAllRoles();

    /**
     * 通过名称获取角色
     *
     * @param roleName 角色名称
     * @return 角色
     */
    SysRole findByName(String roleName);

    /**
     * 创建角色
     *
     * @param role role
     */
    void createRole(SysRole role);

    /**
     * 删除角色
     *
     * @param roleIds 角色id数组
     */
    void deleteRoles(String[] roleIds);

    /**
     * 更新角色
     *
     * @param role role
     */
    void updateRole(SysRole role);
}
