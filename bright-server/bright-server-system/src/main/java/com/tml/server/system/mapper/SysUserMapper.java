package com.tml.server.system.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tml.api.system.entity.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description 用户信息Mapper接口
 * @author JacksonTu
 * @since 2020-08-10 20:30
 * @version 1.0
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 查找用户详细信息
     *
     * @param page 分页对象
     * @param user 用户对象，用于传递查询条件
     * @param <T>  type
     * @return Ipage
     */
    <T> IPage<SysUser> findUserDetailPage(Page<T> page, @Param("user") SysUser user);

    /**
     * 查找用户详细信息
     *
     * @param user 用户对象，用于传递查询条件
     * @return List<User>
     */
    List<SysUser> findUserDetail(@Param("user") SysUser user);

    /**
     * 获取登录用户
     *
     * @param username 用户名
     * @return 用户
     */
    SysUser findSecurityUserByName(String username);

}
