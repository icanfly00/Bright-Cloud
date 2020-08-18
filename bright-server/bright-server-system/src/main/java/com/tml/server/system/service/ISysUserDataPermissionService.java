package com.tml.server.system.service;



import com.baomidou.mybatisplus.extension.service.IService;
import com.tml.api.system.entity.SysUserDataPermission;

import java.util.List;

/**
 * @description 用户与数据权限业务层
 * @author JacksonTu
 * @since 2020-08-10 20:30
 * @version 1.0
 */
public interface ISysUserDataPermissionService extends IService<SysUserDataPermission> {

    /**
     * 通过部门ID删除关联关系
     *
     * @param deptIds 部门id
     */
    void deleteByDeptIds(List<String> deptIds);

    /**
     * 通过用户ID删除关联关系
     *
     * @param userIds 用户id
     */
    void deleteByUserIds(String[] userIds);

    /**
     * 通过用户ID查找关联关系
     *
     * @param userId 用户id
     * @return 关联关系
     */
    String findByUserId(String userId);

    /**
     * 获取用户数据权限
     *
     * @param userId 用户id
     * @return 数据权限
     */
    List<SysUserDataPermission> findUserDataPermissions(Long userId);

}
