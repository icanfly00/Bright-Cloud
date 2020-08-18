package com.tml.server.system.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tml.api.system.entity.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @description 角色Mapper接口
 * @author JacksonTu
 * @since 2020-08-10 20:30
 * @version 1.0
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 通过用户名查找用户角色
     *
     * @param username 用户名
     * @return 用户角色集合
     */
    List<SysRole> findUserRole(String username);

    /**
     * 查找角色详情
     *
     * @param page 分页
     * @param role 角色
     * @param <T>  type
     * @return IPage<User>
     */
    <T> IPage<SysRole> findRolePage(Page<T> page, @Param("role") SysRole role);

}