package com.tml.server.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tml.api.system.entity.SysRoleMenu;
import com.tml.server.system.mapper.SysRoleMenuMapper;
import com.tml.server.system.service.ISysRoleMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * @author JacksonTu
 * @version 1.0
 * @description 角色与菜单关联业务层实现
 * @since 2020-08-10 20:30
 */
@Service("roleMenuService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements ISysRoleMenuService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRoleMenusByRoleId(String[] roleIds) {
        List<String> list = Arrays.asList(roleIds);
        baseMapper.delete(new LambdaQueryWrapper<SysRoleMenu>().in(SysRoleMenu::getRoleId, list));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRoleMenusByMenuId(String[] menuIds) {
        List<String> list = Arrays.asList(menuIds);
        baseMapper.delete(new LambdaQueryWrapper<SysRoleMenu>().in(SysRoleMenu::getMenuId, list));
    }

    @Override
    public List<SysRoleMenu> getRoleMenusByRoleId(String roleId) {
        return baseMapper.selectList(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getRoleId, roleId));
    }

}
