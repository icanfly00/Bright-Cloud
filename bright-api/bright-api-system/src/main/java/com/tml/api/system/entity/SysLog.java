package com.tml.api.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @description 系统日志表
 * @author JacksonTu
 * @since 2020-08-10 20:30
 * @version 1.0
 */
@Data
@TableName("t_sys_log")
public class SysLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 日志ID
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;
    /**
     * 操作用户
     */
    @TableField("USERNAME")
    private String username;
    /**
     * 操作内容
     */
    @TableField("OPERATION")
    private String operation;
    /**
     * 耗时
     */
    @TableField("TIME")
    private Long time;
    /**
     * 操作方法
     */
    @TableField("METHOD")
    private String method;
    /**
     * 方法参数
     */
    @TableField("PARAMS")
    private String params;
    /**
     * 操作者IP
     */
    @TableField("IP")
    private String ip;
    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    private Date createTime;
    /**
     * 操作地点
     */
    @TableField("LOCATION")
    private String location;

    private transient String createTimeFrom;
    private transient String createTimeTo;
}
