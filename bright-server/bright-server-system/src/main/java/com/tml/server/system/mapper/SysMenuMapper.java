package com.tml.server.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tml.api.system.entity.SysMenu;

import java.util.List;

/**
 * @author JacksonTu
 * @version 1.0
 * @description 菜单Mapper接口
 * @since 2020-08-10 20:30
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 获取用户权限集
     *
     * @param username 用户名
     * @return 用户权限集
     */
    List<SysMenu> findUserPermissions(String username);

    /**
     * 获取用户菜单
     *
     * @param username 用户名
     * @return 用户菜单
     */
    List<SysMenu> findUserMenus(String username);
}