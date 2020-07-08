package com.tml.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description 系统角色表
 * @Author TuMingLong
 * @Date 2020/3/31 16:18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_role")
public class SysRole extends Model<SysRole> {

    /**
     * 角色主键
     */
    @TableId(value = "role_id", type = IdType.AUTO)
    private Integer roleId;


    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色标识
     */
    private String roleCode;

    /**
     * 角色描述
     */
    private String roleDesc;

    /**
     * 数据权限类型
     */
    private int dsType;

    /**
     * 数据权限范围
     */
    private String dsScope;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 删除标识（0-正常,1-删除）
     */
    private String delFlag;

    /**
     * 非数据库字段
     * 部门ids
     */
    @TableField(exist = false)
    private List<Integer> roleDepts;
}
