package com.tml.api.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author JacksonTu
 * @version 1.0
 * @description 角色菜单关联表
 * @since 2020-08-10 20:30
 */
@TableName("t_sys_role_menu")
@Data
public class SysRoleMenu implements Serializable {

    private static final long serialVersionUID = -7573904024872252113L;

    @TableField(value = "ROLE_ID")
    private Long roleId;
    @TableField(value = "MENU_ID")
    private Long menuId;
}