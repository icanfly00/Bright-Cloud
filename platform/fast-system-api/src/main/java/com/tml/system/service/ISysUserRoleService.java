package com.tml.system.service;

import com.tml.common.web.service.IBaseService;
import com.tml.system.entity.SysUserRole;

import java.util.List;

/**
 * @Description 用户与角色关系 服务类
 * @Author TuMingLong
 * @Date 2020/4/5 17:06
 */
public interface ISysUserRoleService extends IBaseService<SysUserRole> {

    /**
     * 根据用户id查询用户角色关系
     *
     * @param userId
     * @return
     */
    List<SysUserRole> selectUserRoleListByUserId(Integer userId);
}
