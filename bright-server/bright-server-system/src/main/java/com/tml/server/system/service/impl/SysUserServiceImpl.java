package com.tml.server.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.tml.api.system.entity.SysUser;
import com.tml.api.system.entity.SysUserDataPermission;
import com.tml.api.system.entity.SysUserRole;
import com.tml.common.core.entity.CurrentUser;
import com.tml.common.core.entity.QueryRequest;
import com.tml.common.core.entity.constant.BrightConstant;
import com.tml.common.core.entity.constant.StringConstant;
import com.tml.common.core.exception.BrightException;
import com.tml.common.core.utils.BrightUtil;
import com.tml.common.core.utils.SortUtil;
import com.tml.server.system.mapper.SysUserMapper;
import com.tml.server.system.service.ISysUserDataPermissionService;
import com.tml.server.system.service.ISysUserRoleService;
import com.tml.server.system.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author JacksonTu
 * @version 1.0
 * @description 用户业务层实现类
 * @since 2020-08-10 20:30
 */
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    private final ISysUserRoleService userRoleService;
    private final ISysUserDataPermissionService userDataPermissionService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public SysUser findByName(String username) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUsername, username);
        return this.baseMapper.selectOne(queryWrapper);
    }

    @Override
    public IPage<SysUser> findUserDetailList(SysUser user, QueryRequest request) {
        Page<SysUser> page = new Page<>(request.getPageNum(), request.getPageSize());
        SortUtil.handlePageSort(request, page, "userId", BrightConstant.ORDER_ASC, false);
        return this.baseMapper.findUserDetailPage(page, user);
    }

    @Override
    public SysUser findUserDetail(String username) {
        SysUser param = new SysUser();
        param.setUsername(username);
        List<SysUser> users = this.baseMapper.findUserDetail(param);
        return CollectionUtils.isNotEmpty(users) ? users.get(0) : null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateLoginTime(String username) {
        SysUser user = new SysUser();
        user.setLastLoginTime(new Date());

        this.baseMapper.update(user, new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, username));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createUser(SysUser user) {
        // 创建用户
        user.setCreateTime(new Date());
        user.setAvatar(SysUser.DEFAULT_AVATAR);
        user.setPassword(passwordEncoder.encode(SysUser.DEFAULT_PASSWORD));
        save(user);
        // 保存用户角色
        String[] roles = StringUtils.splitByWholeSeparatorPreserveAllTokens(user.getRoleId(), StringConstant.COMMA);
        setUserRoles(user, roles);
        // 保存用户数据权限关联关系
        String[] deptIds = StringUtils.splitByWholeSeparatorPreserveAllTokens(user.getDeptIds(), StringConstant.COMMA);
        setUserDataPermissions(user, deptIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(SysUser user) {
        // 更新用户
        user.setPassword(null);
        user.setUsername(null);
        user.setCreateTime(null);
        user.setModifyTime(new Date());
        updateById(user);

        String[] userIds = {String.valueOf(user.getUserId())};
        userRoleService.deleteUserRolesByUserId(userIds);
        String[] roles = StringUtils.splitByWholeSeparatorPreserveAllTokens(user.getRoleId(), StringConstant.COMMA);
        setUserRoles(user, roles);

        userDataPermissionService.deleteByUserIds(userIds);
        String[] deptIds = StringUtils.splitByWholeSeparatorPreserveAllTokens(user.getDeptIds(), StringConstant.COMMA);
        setUserDataPermissions(user, deptIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUsers(String[] userIds) {
        List<String> list = Arrays.asList(userIds);
        removeByIds(list);
        // 删除用户角色
        this.userRoleService.deleteUserRolesByUserId(userIds);
        this.userDataPermissionService.deleteByUserIds(userIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateProfile(SysUser user) throws BrightException {
        user.setPassword(null);
        user.setUsername(null);
        user.setStatus(null);
        if (isCurrentUser(user.getUserId())) {
            updateById(user);
        } else {
            throw new BrightException("您无权修改别人的账号信息！");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAvatar(String avatar) {
        SysUser user = new SysUser();
        user.setAvatar(avatar);
        String currentUsername = BrightUtil.getCurrentUsername();
        this.baseMapper.update(user, new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, currentUsername));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePassword(String password) {
        SysUser user = new SysUser();
        user.setPassword(passwordEncoder.encode(password));
        String currentUsername = BrightUtil.getCurrentUsername();
        this.baseMapper.update(user, new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, currentUsername));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resetPassword(String[] usernames) {
        SysUser params = new SysUser();
        params.setPassword(passwordEncoder.encode(SysUser.DEFAULT_PASSWORD));

        List<String> list = Arrays.asList(usernames);
        this.baseMapper.update(params, new LambdaQueryWrapper<SysUser>().in(SysUser::getUsername, list));

    }

    @Override
    public SysUser findSecurityUserByName(String username) {
        return this.baseMapper.findSecurityUserByName(username);
    }

    private void setUserRoles(SysUser user, String[] roles) {
        List<SysUserRole> userRoles = Lists.newArrayList();
        Arrays.stream(roles).forEach(roleId -> {
            SysUserRole userRole = new SysUserRole();
            userRole.setUserId(user.getUserId());
            userRole.setRoleId(Long.valueOf(roleId));
            userRoles.add(userRole);
        });
        userRoleService.saveBatch(userRoles);
    }

    private void setUserDataPermissions(SysUser user, String[] deptIds) {
        List<SysUserDataPermission> userDataPermissions = Lists.newArrayList();
        Arrays.stream(deptIds).forEach(deptId -> {
            SysUserDataPermission permission = new SysUserDataPermission();
            permission.setDeptId(Long.valueOf(deptId));
            permission.setUserId(user.getUserId());
            userDataPermissions.add(permission);
        });
        userDataPermissionService.saveBatch(userDataPermissions);
    }

    private boolean isCurrentUser(Long id) {
        CurrentUser currentUser = BrightUtil.getCurrentUser();
        return currentUser != null && id.equals(currentUser.getUserId());
    }
}
