package com.tml.auth.service;

import com.tml.api.system.entity.SysUserConnection;
import com.tml.auth.entity.BindUser;
import com.tml.common.core.entity.CommonResult;
import com.tml.common.core.exception.BrightException;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthRequest;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import java.util.List;

/**
 * @author JacksonTu
 * @version 1.0
 * @description
 * @since 2020-08-10 20:30
 */
public interface SocialLoginService {

    /**
     * 解析第三方登录请求
     *
     * @param oauthType 第三方平台类型
     * @return AuthRequest
     * @throws BrightException 异常
     */
    AuthRequest renderAuth(String oauthType) throws BrightException;

    /**
     * 处理第三方登录（绑定页面）
     *
     * @param oauthType 第三方平台类型
     * @param callback  回调
     * @return CommonResult
     * @throws BrightException 异常
     */
    CommonResult resolveBind(String oauthType, AuthCallback callback) throws BrightException;

    /**
     * 处理第三方登录（登录页面）
     *
     * @param oauthType 第三方平台类型
     * @param callback  回调
     * @return CommonResult
     * @throws BrightException 异常
     */
    CommonResult resolveLogin(String oauthType, AuthCallback callback) throws BrightException;

    /**
     * 绑定并登录
     *
     * @param bindUser 绑定用户
     * @param authUser 第三方平台对象
     * @return OAuth2AccessToken 令牌对象
     * @throws BrightException 异常
     */
    OAuth2AccessToken bindLogin(BindUser bindUser, AuthUser authUser) throws BrightException;

    /**
     * 注册并登录
     *
     * @param registerUser 注册用户
     * @param authUser     第三方平台对象
     * @return OAuth2AccessToken 令牌对象
     * @throws BrightException 异常
     */
    OAuth2AccessToken signLogin(BindUser registerUser, AuthUser authUser) throws BrightException;

    /**
     * 绑定
     *
     * @param bindUser 绑定对象
     * @param authUser 第三方平台对象
     * @throws BrightException 异常
     */
    void bind(BindUser bindUser, AuthUser authUser) throws BrightException;

    /**
     * 解绑
     *
     * @param bindUser  绑定对象
     * @param oauthType 第三方平台对象
     * @throws BrightException 异常
     */
    void unbind(BindUser bindUser, String oauthType) throws BrightException;

    /**
     * 根据用户名获取绑定关系
     *
     * @param username 用户名
     * @return 绑定关系集合
     */
    List<SysUserConnection> findUserConnections(String username);
}
