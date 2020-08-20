package com.tml.api.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.tml.common.core.converter.ExcelDateTimeConverter;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;


/**
 * @author JacksonTu
 * @version 1.0
 * @description 角色信息表
 * @since 2020-08-10 20:30
 */
@Data
@TableName("t_sys_role")
@Excel("角色信息表")
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
    @ExcelField(value = "角色名称")
    private String roleName;
    /**
     * 角色描述
     */
    @TableField(value = "REMARK")
    @Size(max = 50, message = "{noMoreThan}")
    @ExcelField(value = "角色描述")
    private String remark;
    /**
     * 创建时间
     */
    @TableField(value = "CREATE_TIME")
    @ExcelField(value = "创建时间", writeConverter = ExcelDateTimeConverter.class)
    private Date createTime;
    /**
     * 修改时间
     */
    @TableField(value = "MODIFY_TIME")
    @ExcelField(value = "修改时间", writeConverter = ExcelDateTimeConverter.class)
    private Date modifyTime;

    private transient String menuIds;

}