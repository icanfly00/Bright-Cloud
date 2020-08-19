package com.tml.auth.service.impl;

import cn.hutool.core.util.StrUtil;
import com.tml.api.system.entity.SysUser;
import com.tml.api.system.entity.SysUserConnection;
import com.tml.auth.entity.BindUser;
import com.tml.auth.feign.RemoteUserFeignService;
import com.tml.auth.manager.UserManager;
import com.tml.auth.properties.BrightAuthProperties;
import com.tml.auth.service.SocialLoginService;
import com.tml.common.core.entity.CommonResult;
import com.tml.common.core.entity.ResultBody;
import com.tml.common.core.entity.constant.GrantTypeConstant;
import com.tml.common.core.entity.constant.ParamsConstant;
import com.tml.common.core.entity.constant.SocialConstant;
import com.tml.common.core.entity.constant.StringConstant;
import com.tml.common.core.exception.BrightException;
import com.tml.common.core.utils.BrightUtil;
import com.xkcoding.justauth.AuthRequestFactory;
import lombok.RequiredArgsConstructor;
import me.zhyd.oauth.config.AuthSource;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author JacksonTu
 * @version 1.0
 * @description
 * @since 2020-08-10 20:30
 */
@Service
@RequiredArgsConstructor
public class SocialLoginServiceImpl implements SocialLoginService {

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    private static final String NOT_BIND = "not_bind";
    private static final String SOCIAL_LOGIN_SUCCESS = "social_login_success";

    private final UserManager userManager;
    private final AuthRequestFactory factory;
    private final BrightAuthProperties properties;
    private final PasswordEncoder passwordEncoder;
    private final RemoteUserFeignService remoteUserFeignService;
    private final ResourceOwnerPasswordTokenGranter granter;
    private final RedisClientDetailsService redisClientDetailsService;

    @Override
    public AuthRequest renderAuth(String oauthType) throws BrightException {
        return factory.get(getAuthSource(oauthType));
    }

    @Override
    public CommonResult resolveBind(String oauthType, AuthCallback callback) throws BrightException {
        CommonResult commonResult = new CommonResult();
        AuthRequest authRequest = factory.get(getAuthSource(oauthType));
        AuthResponse<?> response = authRequest.login(resolveAuthCallback(callback));
        if (response.ok()) {
            commonResult.data(response.getData());
        } else {
            throw new BrightException(String.format("第三方登录失败，%s", response.getMsg()));
        }
        return commonResult;
    }

    @Override
    public CommonResult resolveLogin(String oauthType, AuthCallback callback) throws BrightException {
        CommonResult commonResult = new CommonResult();
        AuthRequest authRequest = factory.get(getAuthSource(oauthType));
        AuthResponse<?> response = authRequest.login(resolveAuthCallback(callback));
        if (response.ok()) {
            AuthUser authUser = (AuthUser) response.getData();
            ResultBody<SysUserConnection> resultBody = remoteUserFeignService.findUserConnectionByCondition(authUser.getSource().toString(), authUser.getUuid());
            if (resultBody.getCode() != 200) {
                commonResult.message(NOT_BIND).data(authUser);
            } else {
                SysUserConnection sysUserConnection = resultBody.getData();
                SysUser user = userManager.findByName(sysUserConnection.getUserName());
                if (user == null) {
                    throw new BrightException("系统中未找到与第三方账号对应的账户");
                }
                OAuth2AccessToken oAuth2AccessToken = getOauth2AccessToken(user);
                commonResult.message(SOCIAL_LOGIN_SUCCESS).data(oAuth2AccessToken);
                commonResult.put(USERNAME, user.getUsername());
            }
        } else {
            throw new BrightException(String.format("第三方登录失败，%s", response.getMsg()));
        }
        return commonResult;
    }

    @Override
    public OAuth2AccessToken bindLogin(BindUser bindUser, AuthUser authUser) throws BrightException {
        SysUser sysUser = userManager.findByName(bindUser.getBindUsername());
        if (sysUser == null || !passwordEncoder.matches(bindUser.getBindPassword(), sysUser.getPassword())) {
            throw new BrightException("绑定系统账号失败，用户名或密码错误！");
        }
        this.createConnection(sysUser, authUser);
        return this.getOauth2AccessToken(sysUser);
    }

    @Override
    public OAuth2AccessToken signLogin(BindUser registerUser, AuthUser authUser) throws BrightException {
        SysUser user = this.userManager.findByName(registerUser.getBindUsername());
        if (user != null) {
            throw new BrightException("该用户名已存在！");
        }
        String encryptPassword = passwordEncoder.encode(registerUser.getBindPassword());
        SysUser sysUser = this.userManager.registerUser(registerUser.getBindUsername(), encryptPassword);
        this.createConnection(sysUser, authUser);
        return this.getOauth2AccessToken(sysUser);
    }

    @Override
    public void bind(BindUser bindUser, AuthUser authUser) throws BrightException {
        String username = bindUser.getBindUsername();
        if (isCurrentUser(username)) {
            ResultBody<SysUserConnection> resultBody = remoteUserFeignService.findUserConnectionByCondition(authUser.getSource().toString(), authUser.getUuid());
            if (resultBody.getCode() == 200 && resultBody.getData() != null) {
                throw new BrightException("绑定失败，该第三方账号已绑定" + resultBody.getData().getUserName() + "系统账户");
            }
            SysUser sysUser = new SysUser();
            sysUser.setUsername(username);
            this.createConnection(sysUser, authUser);
        } else {
            throw new BrightException("绑定失败，您无权绑定别人的账号");
        }
    }

    @Override
    public void unbind(BindUser bindUser, String oauthType) throws BrightException {
        String username = bindUser.getBindUsername();
        if (isCurrentUser(username)) {
            this.remoteUserFeignService.deleteUserConnectionByCondition(username, oauthType);
        } else {
            throw new BrightException("解绑失败，您无权解绑别人的账号");
        }
    }

    @Override
    public List<SysUserConnection> findUserConnections(String username) {
        ResultBody<List<SysUserConnection>> resultBody = this.remoteUserFeignService.findUserConnectionByUsername(username);
        if (resultBody.getCode() == 200 && resultBody.getData() != null && resultBody.getData().size() > 0) {
            return resultBody.getData();
        }
        return null;
    }

    private void createConnection(SysUser sysUser, AuthUser authUser) {
        SysUserConnection sysUserConnection = new SysUserConnection();
        sysUserConnection.setUserName(sysUser.getUsername());
        sysUserConnection.setProviderName(authUser.getSource().toString());
        sysUserConnection.setProviderUserId(authUser.getUuid());
        sysUserConnection.setProviderUserName(authUser.getUsername());
        sysUserConnection.setImageUrl(authUser.getAvatar());
        sysUserConnection.setNickName(authUser.getNickname());
        sysUserConnection.setLocation(authUser.getLocation());
        this.remoteUserFeignService.saveUserConnection(sysUserConnection);
    }

    private AuthCallback resolveAuthCallback(AuthCallback callback) {
        int stateLength = 3;
        String state = callback.getState();
        String[] strings = StringUtils.splitByWholeSeparatorPreserveAllTokens(state, StringConstant.DOUBLE_COLON);
        if (strings.length == stateLength) {
            callback.setState(strings[0] + StringConstant.DOUBLE_COLON + strings[1]);
        }
        return callback;
    }

    private AuthSource getAuthSource(String type) throws BrightException {
        if (StrUtil.isNotBlank(type)) {
            return AuthSource.valueOf(type.toUpperCase());
        } else {
            throw new BrightException(String.format("暂不支持%s第三方登录", type));
        }
    }

    private boolean isCurrentUser(String username) {
        String currentUsername = BrightUtil.getCurrentUsername();
        return StringUtils.equalsIgnoreCase(username, currentUsername);
    }

    private OAuth2AccessToken getOauth2AccessToken(SysUser user) throws BrightException {
        final HttpServletRequest httpServletRequest = BrightUtil.getHttpServletRequest();
        httpServletRequest.setAttribute(ParamsConstant.LOGIN_TYPE, SocialConstant.SOCIAL_LOGIN);
        String socialLoginClientId = properties.getSocialLoginClientId();
        ClientDetails clientDetails = null;
        try {
            clientDetails = redisClientDetailsService.loadClientByClientId(socialLoginClientId);
        } catch (Exception e) {
            throw new BrightException("获取第三方登录可用的Client失败");
        }
        if (clientDetails == null) {
            throw new BrightException("未找到第三方登录可用的Client");
        }
        Map<String, String> requestParameters = new HashMap<>(5);
        requestParameters.put(ParamsConstant.GRANT_TYPE, GrantTypeConstant.PASSWORD);
        requestParameters.put(USERNAME, user.getUsername());
        requestParameters.put(PASSWORD, SocialConstant.SOCIAL_LOGIN_PASSWORD);

        String grantTypes = String.join(StringConstant.COMMA, clientDetails.getAuthorizedGrantTypes());
        TokenRequest tokenRequest = new TokenRequest(requestParameters, clientDetails.getClientId(), clientDetails.getScope(), grantTypes);
        return granter.grant(GrantTypeConstant.PASSWORD, tokenRequest);
    }
}
