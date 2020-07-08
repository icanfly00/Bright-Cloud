package com.tml.system.mapper;

import com.tml.common.web.mapper.SuperMapper;
import com.tml.system.entity.SysUserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description com.tml.system.mapper
 * @Author TuMingLong
 * @Date 2020/3/31 17:15
 */
public interface SysUserRoleMapper extends SuperMapper<SysUserRole> {

    /**
     * 根据用户id查询用户角色关系
     *
     * @param userId
     * @return
     */
    List<SysUserRole> selectUserRoleListByUserId(@Param("userId") Integer userId);
}
