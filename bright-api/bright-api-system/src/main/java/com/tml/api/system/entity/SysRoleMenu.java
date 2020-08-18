package com.tml.api.system.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @description 角色菜单关联表
 * @author JacksonTu
 * @since 2020-08-10 20:30
 * @version 1.0
 */
@TableName("t_sys_role_menu")
@Data
public class SysRoleMenu implements Serializable {

    private static final long serialVersionUID = -7573904024872252113L;

    @TableId(value = "ROLE_ID")
    private Long roleId;
    @TableId(value = "MENU_ID")
    private Long menuId;
}