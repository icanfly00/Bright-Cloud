package com.tml.common.security.converter;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * @Description com.tml.common.security.converter
 * @Author TuMingLong
 * @Date 2020/7/3 11:44
 */
public class RestRedisTokenService implements ResourceServerTokenServices {
    private TokenStore tokenStore;

    @Override
    public OAuth2Authentication loadAuthentication(String accessToken) throws AuthenticationException, InvalidTokenException {
        OAuth2Authentication oAuth2Authentication = tokenStore.readAuthentication(accessToken);
        return oAuth2Authentication;
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
}
