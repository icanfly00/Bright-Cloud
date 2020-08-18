package com.tml.api.system.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @description 
 * @author JacksonTu
 * @since 2020-08-10 20:30
 * @version 1.0
 */
@Data
@TableName("t_sys_user_connection")
public class SysUserConnection {

    @TableField(value = "USER_NAME")
    @NotBlank(message = "{required}")
    private String userName;

    @TableField(value = "PROVIDER_NAME")
    @NotBlank(message = "{required}")
    private String providerName;

    @TableField(value = "PROVIDER_USER_ID")
    @NotBlank(message = "{required}")
    private String providerUserId;

    @TableField(value = "PROVIDER_USER_NAME")
    private String providerUserName;

    @TableField(value = "NICK_NAME")
    private String nickName;

    @TableField("IMAGE_URL")
    private String imageUrl;

    @TableField("LOCATION")
    private String location;

    @TableField("REMARK")
    private String remark;

}