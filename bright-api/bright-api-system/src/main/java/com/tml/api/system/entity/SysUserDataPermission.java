package com.tml.api.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author JacksonTu
 * @version 1.0
 * @description 用户数据权限关联表
 * @since 2020-08-10 20:30
 */
@Data
@TableName("t_sys_user_data_permission")
public class SysUserDataPermission {

    @TableField("USER_ID")
    private Long userId;
    @TableField("DEPT_ID")
    private Long deptId;

}