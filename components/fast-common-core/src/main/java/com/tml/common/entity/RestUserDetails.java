package com.tml.common.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;


/**
 * @Dercription 用户手机号和账号密码 身份权限认证类 登陆身份认证
 * @Author TuMingLong
 * @Date 2020/4/21 15:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class RestUserDetails implements UserDetails {

    private LoginType loginType = LoginType.normal;
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 登录名
     */
    private String username;
    /**
     * 密码
     */
    private String password;

    /**
     * 用户权限
     */
    private Collection<? extends GrantedAuthority> authorities;
    /**
     * 是否已锁定
     */
    private boolean accountNonLocked;
    /**
     * 是否已过期
     */
    private boolean accountNonExpired;
    /**
     * 是否启用
     */
    private boolean enabled;
    /**
     * 密码是否已过期
     */
    private boolean credentialsNonExpired;
    /**
     * 认证客户端ID
     */
    private String clientId;
    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像
     */
    private String avatar;
    /**
     * 手机号
     */
    private String phone;

    /**
     * 企业ID
     */
    private Integer enterpriseId;

    /**
     * 数据权限
     */
    private String enterpriseIdList;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }


    /**
     * 账户是否未过期,过期无法验证
     *
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    /**
     * 指定用户是否解锁,锁定的用户无法进行身份验证
     *
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    /**
     * 指示是否已过期的用户的凭据(密码),过期的凭据防止认证
     *
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    /**
     * 是否可用 ,禁用的用户不能身份验证
     *
     * @return
     */
    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

}
