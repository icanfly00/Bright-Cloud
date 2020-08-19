package com.tml.server.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tml.api.system.entity.SysUserDataPermission;

import java.util.List;

/**
 * @author JacksonTu
 * @version 1.0
 * @description 用户与数据权限Mapper接口
 * @since 2020-08-10 20:30
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
