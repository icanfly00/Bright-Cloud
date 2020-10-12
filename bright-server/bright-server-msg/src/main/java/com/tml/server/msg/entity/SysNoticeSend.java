package com.tml.server.msg.entity;

import java.util.Date;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
* 用户通告阅读标记表 Entity
*
* @author JacksonTu
* @date 2020-10-12 15:03:04
*/
@Data
@TableName("t_sys_notice_send")
public class SysNoticeSend {

    /**
     * 
     */
    @TableField("ID")
    private Long id;

    /**
     * 通告ID
     */
    @TableField("NOTICE_ID")
    private Long noticeId;

    /**
     * 用户id
     */
    @TableField("USER_ID")
    private Long userId;

    /**
     * 阅读状态（0未读，1已读）
     */
    @TableField("READ_FLAG")
    private String readFlag;

    /**
     * 阅读时间
     */
    @TableField("READ_TIME")
    private Date readTime;

    /**
     * 创建人
     */
    @TableField("CREATE_USER")
    private String createUser;

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    private Date createTime;

    /**
     * 更新人
     */
    @TableField("UPDATE_USER")
    private String updateUser;

    /**
     * 更新时间
     */
    @TableField("UPDATE_TIME")
    private Date updateTime;

}