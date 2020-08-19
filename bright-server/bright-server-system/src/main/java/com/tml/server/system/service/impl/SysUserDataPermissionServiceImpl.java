package com.tml.server.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tml.api.system.entity.SysUserDataPermission;
import com.tml.common.core.entity.constant.StringConstant;
import com.tml.server.system.mapper.SysUserDataPermissionMapper;
import com.tml.server.system.service.ISysUserDataPermissionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author JacksonTu
 * @version 1.0
 * @description 用户与数据权限业务层实现
 * @since 2020-08-10 20:30
 */
@Service("userDataPermissionService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class SysUserDataPermissionServiceImpl extends ServiceImpl<SysUserDataPermissionMapper, SysUserDataPermission> implements ISysUserDataPermissionService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByDeptIds(List<String> deptIds) {
        this.baseMapper.delete(new LambdaQueryWrapper<SysUserDataPermission>().in(SysUserDataPermission::getDeptId, deptIds));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByUserIds(String[] userIds) {
        List<String> list = Arrays.asList(userIds);
        this.baseMapper.delete(new LambdaQueryWrapper<SysUserDataPermission>().in(SysUserDataPermission::getUserId, list));
    }

    @Override
    public String findByUserId(String userId) {
        LambdaQueryWrapper<SysUserDataPermission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUserDataPermission::getUserId, userId);
        return this.baseMapper.selectList(wrapper).stream().map(permission -> String.valueOf(permission.getDeptId())).collect(Collectors.joining(StringConstant.COMMA));
    }

    @Override
    public List<SysUserDataPermission> findUserDataPermissions(Long userId) {
        return this.baseMapper.findUserDataPermissions(userId);
    }
}
