package com.tml.server.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tml.api.system.entity.SysRoleMenu;

import java.util.List;

/**
 * @author JacksonTu
 * @version 1.0
 * @description 角色与菜单关联业务层
 * @since 2020-08-10 20:30
 */
public interface ISysRoleMenuService extends IService<SysRoleMenu> {

    /**
     * 删除角色菜单关联数据
     *
     * @param roleIds 角色id
     */
    void deleteRoleMenusByRoleId(String[] roleIds);

    /**
     * 删除角色菜单关联数据
     *
     * @param menuIds 菜单id
     */
    void deleteRoleMenusByMenuId(String[] menuIds);

    /**
     * 获取角色对应的菜单列表
     *
     * @param roleId 角色id
     * @return 菜单列表
     */
    List<SysRoleMenu> getRoleMenusByRoleId(String roleId);
}
