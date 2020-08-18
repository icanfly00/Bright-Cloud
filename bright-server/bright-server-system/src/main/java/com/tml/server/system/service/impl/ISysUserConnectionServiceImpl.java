package com.tml.server.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tml.api.system.entity.SysUserConnection;
import com.tml.server.system.mapper.SysUserConnectionMapper;
import com.tml.server.system.service.ISysUserConnectionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author JacksonTu
 * @version 1.0
 * @description
 * @since 2020-08-10 20:30
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ISysUserConnectionServiceImpl extends ServiceImpl<SysUserConnectionMapper, SysUserConnection> implements ISysUserConnectionService {

    @Override
    public SysUserConnection selectByCondition(String providerName, String providerUserId) {
        LambdaQueryWrapper<SysUserConnection> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserConnection::getProviderName, providerName)
                .eq(SysUserConnection::getProviderUserId, providerUserId);
        return this.baseMapper.selectOne(queryWrapper);
    }

    @Override
    public List<SysUserConnection> selectByCondition(String username) {
        LambdaQueryWrapper<SysUserConnection> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserConnection::getUserName, username);
        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createUserConnection(SysUserConnection sysUserConnection) {
        this.baseMapper.insert(sysUserConnection);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByCondition(String username, String providerName) {
        LambdaQueryWrapper<SysUserConnection> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserConnection::getUserName, username);
        queryWrapper.eq(SysUserConnection::getProviderName, providerName);
        this.remove(queryWrapper);
    }

}
