package com.tml.server.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tml.api.system.entity.SysUserRole;
import com.tml.common.core.entity.constant.StringConstant;
import com.tml.server.system.mapper.SysUserRoleMapper;
import com.tml.server.system.service.ISysUserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author JacksonTu
 * @version 1.0
 * @description
 * @since 2020-08-10 20:30
 */
@Service("userRoleService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements ISysUserRoleService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUserRolesByRoleId(String[] roleIds) {
        List<String> list = Arrays.asList(roleIds);
        this.baseMapper.delete(new LambdaQueryWrapper<SysUserRole>().in(SysUserRole::getRoleId, list));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUserRolesByUserId(String[] userIds) {
        List<String> list = Arrays.asList(userIds);
        this.baseMapper.delete(new LambdaQueryWrapper<SysUserRole>().in(SysUserRole::getUserId, list));
    }

    @Override
    public List<String> findUserIdsByRoleId(String[] roleIds) {
        List<SysUserRole> list = baseMapper.selectList(new LambdaQueryWrapper<SysUserRole>().in(SysUserRole::getRoleId, String.join(StringConstant.COMMA, roleIds)));
        return list.stream().map(userRole -> String.valueOf(userRole.getUserId())).collect(Collectors.toList());
    }

}
