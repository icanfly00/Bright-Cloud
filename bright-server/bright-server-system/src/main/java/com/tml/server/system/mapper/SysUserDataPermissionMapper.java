package com.tml.server.system.mapper;

import com.tml.api.system.entity.SysUserDataPermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @description 用户与数据权限Mapper接口
 * @author JacksonTu
 * @since 2020-08-10 20:30
 * @version 1.0
 */
public interface SysUserDataPermissionMapper extends BaseMapper<SysUserDataPermission> {
    /**
     * 获取用户数据权限
     *
     * @param userId 用户id
     * @return 数据权限
     */
    List<SysUserDataPermission> findUserDataPermissions(Long userId);

}
