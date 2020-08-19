package com.tml.server.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tml.api.system.entity.SysMenu;
import com.tml.common.core.entity.router.VueRouter;

import java.util.List;
import java.util.Map;

/**
 * @author JacksonTu
 * @version 1.0
 * @description 菜单业务层
 * @since 2020-08-10 20:30
 */
public interface ISysMenuService extends IService<SysMenu> {

    /**
     * 获取用户权限
     *
     * @param username 用户名
     * @return 用户权限
     */
    String findUserPermissions(String username);

    /**
     * 获取用户菜单
     *
     * @param username 用户名
     * @return 用户菜单
     */
    List<SysMenu> findUserMenus(String username);

    /**
     * 获取用户菜单
     *
     * @param menu menu
     * @return 用户菜单
     */
    Map<String, Object> findMenus(SysMenu menu);

    /**
     * 获取用户路由
     *
     * @param username 用户名
     * @return 用户路由
     */
    List<VueRouter<SysMenu>> getUserRouters(String username);

    /**
     * 获取菜单列表
     *
     * @param menu menu
     * @return 菜单列表
     */
    List<SysMenu> findMenuList(SysMenu menu);

    /**
     * 创建菜单
     *
     * @param menu menu
     */
    void createMenu(SysMenu menu);

    /**
     * 更新菜单
     *
     * @param menu menu
     */
    void updateMenu(SysMenu menu);

    /**
     * 递归删除菜单/按钮
     *
     * @param menuIds menuIds
     */
    void deleteMenus(String[] menuIds);

}
