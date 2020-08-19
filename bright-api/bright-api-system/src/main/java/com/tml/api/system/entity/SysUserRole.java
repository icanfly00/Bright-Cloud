package com.tml.api.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author JacksonTu
 * @version 1.0
 * @description 用户角色关联表
 * @since 2020-08-10 20:30
 */
@Data
@TableName("t_sys_user_role")
public class SysUserRole implements Serializable {

    private static final long serialVersionUID = -3166012934498268403L;

    @TableField(value = "USER_ID")
    private Long userId;

    @TableField(value = "ROLE_ID")
    private Long roleId;

}