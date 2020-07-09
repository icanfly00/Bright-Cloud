package com.tml.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @Description OAuth2.0客户端信息
 * @Author TuMingLong
 * @Date 2020/5/10 16:12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("oauth_client_details")
public class OauthClientDetails extends Model<OauthClientDetails> {

    private static final long serialVersionUID = 421783821058285802L;

    @TableId(value = "client_id")
    @NotBlank(message = "{required}")
    @Size(max = 255, message = "{noMoreThan}")
    private String clientId;

    @TableField("resource_ids")
    @Size(max = 255, message = "{noMoreThan}")
    private String resourceIds;

    @TableField("client_secret")
    @NotBlank(message = "{required}")
    @Size(max = 255, message = "{noMoreThan}")
    private String clientSecret;

    @TableField("scope")
    @NotBlank(message = "{required}")
    @Size(max = 255, message = "{noMoreThan}")
    private String scope;

    @TableField("authorized_grant_types")
    @NotBlank(message = "{required}")
    @Size(max = 255, message = "{noMoreThan}")
    private String authorizedGrantTypes;

    @TableField("web_server_redirect_uri")
    @Size(max = 255, message = "{noMoreThan}")
    private String webServerRedirectUri;

    @TableField("authorities")
    @Size(max = 255, message = "{noMoreThan}")
    private String authorities;

    @TableField("access_token_validity")
    @NotNull(message = "{required}")
    private Integer accessTokenValidity;

    @TableField("refresh_token_validity")
    private Integer refreshTokenValidity;

    @TableField("autoapprove")
    private String autoapprove;

    @TableField(value = "origin_secret", exist = false)
    private String originSecret;

}
