package com.tml.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.tml.common.constant.CacheConstant;
import com.tml.common.web.service.impl.BaseServiceImpl;
import com.tml.system.entity.SysUser;
import com.tml.system.mapper.SysUserMapper;
import com.tml.system.service.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Description 用户管理 服务类实现
 * @Author TuMingLong
 * @Date 2020/3/31 18:36
 */
@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Resource
    private ISysMenuService menuService;

    @Resource
    private ISysUserRoleService userRoleService;

    @Resource
    private ISysDeptService deptService;

    @Resource
    private ISysJobService jobService;

    @SentinelResource(value = "findPermsByUserId", blockHandler = "userIdBlockHandler", fallback = "userIdFallback")
    @Cacheable(cacheNames = CacheConstant.SYS_USERS_PERMS_CACHE, key = "#userId", unless = "#result==null")
    @Override
    public Set<String> findPermsByUserId(Integer userId) {
        return menuService.findPermsByUserId(userId)
                .stream()
                .filter(StringUtils::isNoneBlank)
                .collect(Collectors.toSet());
    }

    @SentinelResource(value = "findRoleIdByUserId", blockHandler = "userIdBlockHandler", fallback = "userIdFallback")
    @Cacheable(cacheNames = CacheConstant.SYS_USERS_ROLES_CACHE, key = "#userId", unless = "#result==null")
    @Override
    public Set<String> findRoleIdByUserId(Integer userId) {
        return userRoleService.selectUserRoleListByUserId(userId)
                .stream()
                .map(sysUserRole -> "ROLE_" + sysUserRole.getId())
                .collect(Collectors.toSet());
    }

    @SentinelResource(value = "findDataPermsByUserId", blockHandler = "findDataPermsByUserIdBlockHandler", fallback = "findDataPermsByUserIdFallback")
    @Cacheable(cacheNames = CacheConstant.SYS_DATA_PERMISSIONS_CACHE, key = "#userId", unless = "#result==null")
    @Override
    public List<Integer> findDataPermsByUserId(Integer userId) {
        return this.baseMapper.findDataPermsByUserId(userId);
    }

    @Cacheable(cacheNames = CacheConstant.SYS_USERS_CACHE, key = "#username", unless = "#result==null")
    @Override
    public SysUser findByUsername(String username) {
        SysUser sysUser = this.baseMapper.selectOne(Wrappers.<SysUser>lambdaQuery()
                .select(SysUser::getUserId, SysUser::getUsername, SysUser::getPhone, SysUser::getEmail, SysUser::getPassword, SysUser::getDeptId, SysUser::getJobId, SysUser::getAvatar, SysUser::getLockFlag)
                .eq(SysUser::getUsername, username));
        //TODO:获取部门
        sysUser.setDeptName(deptService.selectDeptNameByDeptId(sysUser.getDeptId()));
        //TODO:获取岗位
        sysUser.setJobName(jobService.selectJobNameByJobId(sysUser.getJobId()));
        return sysUser;
    }

    @Override
    public boolean checkUser(SysUser sysUser) {
        SysUser user = findSecurityUserByUser(sysUser);
        //TODO:情况1：根据用户信息查询，该用户不存在
        if (ObjectUtils.isEmpty(user)) {
            throw new ApiException("该用户不存在，请注册");
        }
        //TODO:情况2：根据用户信息查询，该用户已冻结
        if (user.getLockFlag().equals("1")) {
            throw new ApiException("该用户已冻结");
        }
        return true;
    }

    @SentinelResource(value = "findSecurityUserByUser", blockHandler = "findSecurityUserByUserBlockHandler", fallback = "findSecurityUserByUserFallback")
    @Cacheable(cacheNames = CacheConstant.SYS_USERS_CACHE, key = "#p0.username", unless = "#result==null")
    @Override
    public SysUser findSecurityUserByUser(SysUser sysUser) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(StringUtils.isNoneBlank(sysUser.getUsername()),SysUser::getUsername,sysUser.getUsername())
                .eq(StringUtils.isNoneBlank(sysUser.getPhone()),SysUser::getPhone,sysUser.getPhone())
                .eq(sysUser.getUserId()!=null && sysUser.getUserId()!=0,SysUser::getUserId,sysUser.getUserId())
        ;
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public void updateLoginTime(String username) {
        SysUser user = new SysUser();
        user.setLoginTime(LocalDateTime.now());

        this.baseMapper.update(user, new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, username));
    }

    public SysUser findSecurityUserByUserBlockHandler(SysUser sysUser, BlockException ex) {
        log.error("findSecurityUserByUserBlockHandler: " + ex.getMessage());
        return null;
    }

    public SysUser findSecurityUserByUserFallback(SysUser sysUser, Throwable throwable) {
        log.error("findSecurityUserByUserFallback: " + throwable.getMessage());
        return null;
    }

    public Set<String> userIdBlockHandler(Integer userId, BlockException ex) {
        log.error("userIdBlockHandler: " + ex.getMessage());
        return new HashSet<>();
    }

    public Set<String> userIdFallback(Integer userId, Throwable throwable) {
        log.error("userIdBlockHandler: " + throwable.getMessage());
        return new HashSet<>();
    }

    public ArrayList<Integer> findDataPermsByUserIdBlockHandler(Integer userId, BlockException ex) {
        log.error("findDataPermsByUserIdBlockHandler: " + ex.getMessage());
        return new ArrayList<>();
    }

    public ArrayList<Integer> findDataPermsByUserIdFallback(Integer userId, Throwable throwable) {
        log.error("findDataPermsByUserIdFallback: " + throwable.getMessage());
        return new ArrayList<>();
    }
}
