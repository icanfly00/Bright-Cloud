package com.tml.server.system.service;

import com.tml.api.system.entity.SysRoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @description 角色与菜单关联业务层
 * @author JacksonTu
 * @since 2020-08-10 20:30
 * @version 1.0
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
