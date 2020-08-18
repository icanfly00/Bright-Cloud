package com.tml.api.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;


/**
 * @description 角色信息表
 * @author JacksonTu
 * @since 2020-08-10 20:30
 * @version 1.0
 */
@Data
@TableName("t_sys_role")
public class SysRole implements Serializable {

    private static final long serialVersionUID = -1714476694755654924L;

    @TableId(value = "ROLE_ID", type = IdType.AUTO)
    private Long roleId;
    /**
     * 角色名称
     */
    @TableField(value = "ROLE_NAME")
    @NotBlank(message = "{required}")
    @Size(max = 10, message = "{noMoreThan}")
    private String roleName;
    /**
     * 角色描述
     */
    @TableField(value = "REMARK")
    @Size(max = 50, message = "{noMoreThan}")
    private String remark;
    /**
     * 创建时间
     */
    @TableField(value = "CREATE_TIME")
    private Date createTime;
    /**
     * 修改时间
     */
    @TableField(value = "MODIFY_TIME")
    private Date modifyTime;

    private transient String menuIds;

}