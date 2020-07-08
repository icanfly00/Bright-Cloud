package com.tml.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.List;


/**
 * @Description com.tml.system.entity
 * @Author TuMingLong
 * @Date 2020/3/31 16:16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_user")
public class SysUser extends Model<SysUser> {

    /**
     * 主键ID
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    @Length(max = 64, message = "用户名不能超过64个字符")
    @Pattern(regexp = "^[\\u4E00-\\u9FA5A-Za-z0-9\\*]*$", message = "用户名限制：最多64个字符，包含文字、字母和数字")
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 企业ID
     */
    private Integer enterpriseId;

    /**
     * 部门ID
     */
    private Integer deptId;

    /**
     * 岗位ID
     */
    private Integer jobId;

    /**
     * 邮箱
     */
    @Email
    @Pattern(regexp = "[a-zA-Z_]{0,}[0-9]{0,}@(([a-zA-z0-9]-*){1,}\\\\.){1,3}[a-zA-z\\\\-]{1,}", message = "邮箱格式不正确")
    private String email;

    /**
     * 手机号
     */
    @Pattern(regexp = "^[1](([3|5|8][\\\\d])|([4][4,5,6,7,8,9])|([6][2,5,6,7])|([7][^9])|([9][1,8,9]))[\\\\d]{8}$", message = "手机号格式不正确")
    private String phone;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 登录时间
     */
    private LocalDateTime LoginTime;

    /**
     * 0-正常，1-锁定
     */
    private String lockFlag;

    /**
     * 0-正常，1-删除
     */
    private String delFlag;

    /**
     * 角色列表
     */
    @TableField(exist = false)
    private List<SysRole> roleList;
    /**
     * 非数据库字段
     * 部门名称
     */
    @TableField(exist = false)
    private String deptName;

    /**
     * 非数据库字段
     * 岗位名称
     */
    @TableField(exist = false)
    private String jobName;


    @TableField(exist = false)
    private String key;
}
