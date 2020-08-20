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
 * @description 部门信息表
 * @since 2020-08-10 20:30
 */
@Data
@TableName("t_sys_dept")
@Excel("部门信息表")
public class SysDept implements Serializable {

    private static final long serialVersionUID = -7790334862410409053L;

    public static final Long TOP_DEPT_ID = 0L;
    /**
     * 部门ID
     */
    @TableId(value = "DEPT_ID", type = IdType.AUTO)
    private Long deptId;
    /**
     * 部门父ID
     */
    @TableField(value = "PARENT_ID")
    private Long parentId;
    /**
     * 部门名称
     */
    @NotBlank(message = "{required}")
    @Size(max = 20, message = "{noMoreThan}")
    @ExcelField(value = "部门名称")
    private String deptName;
    /**
     * 排序
     */
    @TableField(value = "ORDER_NUM")
    @ExcelField(value = "排序")
    private Integer orderNum;
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

    private transient String createTimeFrom;

    private transient String createTimeTo;

}