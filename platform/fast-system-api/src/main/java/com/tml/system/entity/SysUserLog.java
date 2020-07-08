package com.tml.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.time.LocalDateTime;

/**
 * @Description 登录日志
 * @Author TuMingLong
 * @Date 2020/6/3 16:39
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_user_log")
public class SysUserLog extends Model<SysUserLog> {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 登录用户
     */
    private String username;

    /**
     * 登录时间
     */
    private LocalDateTime loginTime;

    /**
     * 登录地点
     */
    private String location;
    /**
     * 登录 IP
     */
    private String ip;
    /**
     * 操作系统
     */
    private String os;
    /**
     * 登录浏览器
     */
    private String browser;

    /**
     * 是否删除  -1：已删除  0：正常
     */
    private int delFlag;
}
