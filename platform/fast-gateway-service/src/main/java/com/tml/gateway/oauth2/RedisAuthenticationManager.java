package com.tml.gateway.oauth2;

import com.tml.common.api.ResultCode;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import reactor.core.publisher.Mono;

/**
 * @Description 自定义认证接口管理类
 * @Author TuMingLong
 * @Date 2020/7/29 20:27
 */
public class RedisAuthenticationManager implements ReactiveAuthenticationManager {

    private TokenStore tokenStore;

    public RedisAuthenticationManager(RedisTokenStore redisTokenStore) {
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {

        return Mono.just(authentication)
                .filter(a-> a instanceof BearerTokenAuthenticationToken)
                .cast(BearerTokenAuthenticationToken.class)
                .map(BearerTokenAuthenticationToken::getToken)
                .flatMap((token ->{
                    OAuth2Authentication oAuth2Authentication=this.tokenStore.readAuthentication(token);
                    if(oAuth2Authentication==null){
                        return Mono.error(new InvalidTokenException(ResultCode.INVALID_TOKEN.getMessage()));
                    }else {
                        return Mono.just(oAuth2Authentication);
                    }
                })).cast(Authentication.class);
    }

    public TokenStore getTokenStore() {
        return tokenStore;
    }

    public void setTokenStore(TokenStore tokenStore) {
        this.tokenStore = tokenStore;
    }
}
