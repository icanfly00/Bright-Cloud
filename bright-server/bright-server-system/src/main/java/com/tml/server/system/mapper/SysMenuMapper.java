package com.tml.server.system.mapper;

import com.tml.api.system.entity.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;

/**
 * @description 菜单Mapper接口
 * @author JacksonTu
 * @since 2020-08-10 20:30
 * @version 1.0
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