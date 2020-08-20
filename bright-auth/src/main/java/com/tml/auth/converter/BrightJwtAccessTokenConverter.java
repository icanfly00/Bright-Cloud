package com.tml.auth.converter;

import com.tml.auth.constant.SecurityConstant;
import com.tml.common.core.entity.BrightAuthUser;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.HashMap;
import java.util.Map;


/**
 * @author 自定义JwtAccessToken转换器
 * @version 1.0
 * @description
 * @since 2020-08-20 20:30
 */
public class BrightJwtAccessTokenConverter extends JwtAccessTokenConverter {
    /**
     * 生成token
     *
     * @param accessToken
     * @param authentication
     * @return
     */
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        DefaultOAuth2AccessToken defaultOAuth2AccessToken = new DefaultOAuth2AccessToken(accessToken);
        if (authentication.getPrincipal() != null && authentication.getPrincipal() instanceof BrightAuthUser) {
            // 设置额外用户信息
            BrightAuthUser baseUser = ((BrightAuthUser) authentication.getPrincipal());
            final Map<String, Object> additionalInfo = new HashMap<>(8);
            additionalInfo.put(SecurityConstant.USER_ID, baseUser.getUserId());
            defaultOAuth2AccessToken.setAdditionalInformation(additionalInfo);
        }

        return super.enhance(defaultOAuth2AccessToken, authentication);
    }

    /**
     * 解析token
     *
     * @param value
     * @param map
     * @return
     */
    @Override
    public OAuth2AccessToken extractAccessToken(String value, Map<String, ?> map) {
        OAuth2AccessToken oauth2AccessToken = super.extractAccessToken(value, map);
        return oauth2AccessToken;
    }
}
