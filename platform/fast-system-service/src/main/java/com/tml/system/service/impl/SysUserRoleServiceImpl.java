package com.tml.system.service.impl;

import com.tml.common.web.service.impl.BaseServiceImpl;
import com.tml.system.entity.SysUserRole;
import com.tml.system.mapper.SysUserRoleMapper;
import com.tml.system.service.ISysUserRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description 用户与角色关系 服务类实现
 * @Author TuMingLong
 * @Date 2020/4/5 17:07
 */
@Service
public class SysUserRoleServiceImpl extends BaseServiceImpl<SysUserRoleMapper, SysUserRole> implements ISysUserRoleService {


    @Override
    public List<SysUserRole> selectUserRoleListByUserId(Integer userId) {
        return this.baseMapper.selectUserRoleListByUserId(userId);
    }
}
