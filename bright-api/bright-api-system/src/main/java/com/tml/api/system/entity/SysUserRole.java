package com.tml.api.system.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @description 用户角色关联表
 * @author JacksonTu
 * @since 2020-08-10 20:30
 * @version 1.0
 */
@Data
@TableName("t_sys_user_role")
public class SysUserRole implements Serializable {

    private static final long serialVersionUID = -3166012934498268403L;

    @TableId(value = "USER_ID")
    private Long userId;

    @TableId(value = "ROLE_ID")
    private Long roleId;

}