package com.tml.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Description 登录对象
 * @Author TuMingLong
 * @Date 2020/4/6 12:31
 */
@ApiModel(value = "登录对象", description = "登录对象")
@Data
@EqualsAndHashCode(callSuper = false)
public class LoginDto implements Serializable {

    @NotNull(message = "账号不能为空")
    @ApiModelProperty(value = "账号")
    private String username;

    @NotNull(message = "密码不能为空")
    @ApiModelProperty(value = "密码")
    private String password;

    @NotNull(message = "验证码不能为空")
    @ApiModelProperty(value = "验证码")
    private String captcha;

    @ApiModelProperty(value = "验证码key")
    private String checkKey;
}
