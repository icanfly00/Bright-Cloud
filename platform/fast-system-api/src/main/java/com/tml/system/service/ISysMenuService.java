package com.tml.system.service;

import com.tml.common.web.service.IBaseService;
import com.tml.system.entity.SysMenu;

import java.util.List;

/**
 * @Description 菜单服务类
 * @Author TuMingLong
 * @Date 2020/4/5 16:41
 */
public interface ISysMenuService extends IBaseService<SysMenu> {

    /**
     * 根据用户id查询权限
     *
     * @param userId
     * @return
     */
    List<String> findPermsByUserId(Integer userId);
}
