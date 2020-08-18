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
 * @description 部门信息表
 * @author JacksonTu
 * @since 2020-08-10 20:30
 * @version 1.0
 */
@Data
@TableName("t_sys_dept")
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
     *部门名称
     */
    @NotBlank(message = "{required}")
    @Size(max = 20, message = "{noMoreThan}")
    private String deptName;
    /**
     * 排序
     */
    @TableField(value = "ORDER_NUM")
    private Integer orderNum;
    /**
     *创建时间
     */
    @TableField(value = "CREATE_TIME")
    private Date createTime;
    /**
     *修改时间
     */
    @TableField(value = "MODIFY_TIME")
    private Date modifyTime;

    private transient String createTimeFrom;

    private transient String createTimeTo;

}