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


/**
 * @Description 岗位表
 * @Author TuMingLong
 * @Date 2020/3/31 16:48
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_job")
public class SysJob extends Model<SysJob> {

    /**
     * 主键
     */
    @TableId(value = "job_id", type = IdType.AUTO)
    private Integer jobId;

    /**
     * 岗位名称
     */
    private String jobName;

    /**
     * 部门id
     */
    private Integer deptId;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 是否删除  -1：已删除  0：正常
     */
    private String delFlag;


    /**
     * 非数据库字段
     * 所属部门
     */
    @TableField(exist = false)
    private String deptName;
}
