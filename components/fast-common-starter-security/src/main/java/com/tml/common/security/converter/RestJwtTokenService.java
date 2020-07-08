package com.tml.common.security.converter;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.Map;

/**
 * @Description com.tml.common.security.converter
 * @Author TuMingLong
 * @Date 2020/7/3 13:25
 */
public class RestJwtTokenService implements ResourceServerTokenServices {
    private TokenStore tokenStore;

    private DefaultAccessTokenConverter defaultAccessTokenConverter;

    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Override
    public OAuth2Authentication loadAuthentication(String accessToken) throws AuthenticationException, InvalidTokenException {
        OAuth2Authentication auth2Authentication = tokenStore.readAuthentication(accessToken);
        Map<String, ?> map = jwtAccessTokenConverter.convertAccessToken(readAccessToken(accessToken), auth2Authentication);
        return defaultAccessTokenConverter.extractAuthentication(map);
    }

    @Override
    public OAuth2AccessToken readAccessToken(String accessToken) {
        return tokenStore.readAccessToken(accessToken);
    }

    public TokenStore getTokenStore() {
        return tokenStore;
    }

    public void setTokenStore(TokenStore tokenStore) {
        this.tokenStore = tokenStore;
    }

    public DefaultAccessTokenConverter getDefaultAccessTokenConverter() {
        return defaultAccessTokenConverter;
    }

    public void setDefaultAccessTokenConverter(DefaultAccessTokenConverter defaultAccessTokenConverter) {
        this.defaultAccessTokenConverter = defaultAccessTokenConverter;
    }

    public JwtAccessTokenConverter getJwtAccessTokenConverter() {
        return jwtAccessTokenConverter;
    }

    public void setJwtAccessTokenConverter(JwtAccessTokenConverter jwtAccessTokenConverter) {
        this.jwtAccessTokenConverter = jwtAccessTokenConverter;
    }
}
