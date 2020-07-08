package com.tml.system.mapper;

import com.tml.common.web.mapper.SuperMapper;
import com.tml.system.entity.SysMenu;
import com.tml.system.entity.SysRole;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Description 角色管理 Mapper接口
 * @Author TuMingLong
 * @Date 2020/3/31 17:15
 */
public interface SysRoleMapper extends SuperMapper<SysRole> {
    @Insert("insert into sys_role (role_name,role_code,role_desc,ds_type,ds_scope) values (#{roleName}, #{roleCode},#{roleDesc},#{dsType},#{dsScope})")
    @Options(useGeneratedKeys = true, keyProperty = "roleId", keyColumn = "role_id")
    Boolean insertRole(SysRole sysRole);

    /**
     * @param roleId
     * @return
     */
    @Select("select m.menu_id,m.name,m.type,m.parent_id,m.sort,m.perms from sys_menu m, sys_role_menu rm where rm.role_id = #{roleId} and m.menu_id = rm.menu_id")
    List<SysMenu> findMenuListByRoleId(int roleId);

    /**
     * 通过用户ID，查询角色信息
     *
     * @param userId
     * @return
     */
    @Select("SELECT r.* FROM sys_role r, sys_user_role ur WHERE r.role_id = ur.role_id AND r.del_flag = 0 and  ur.user_id IN (#{userId})")
    List<SysRole> listRolesByUserId(Integer userId);
}
