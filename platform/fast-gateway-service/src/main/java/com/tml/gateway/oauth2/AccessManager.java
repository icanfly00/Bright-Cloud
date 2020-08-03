package com.tml.gateway.oauth2;

import cn.hutool.core.collection.ConcurrentHashSet;
import com.tml.gateway.configuration.APIProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @Description 权限管理器
 * @Author TuMingLong
 * @Date 2020/8/1
 */
@Slf4j
public class AccessManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    private APIProperties apiProperties;

    private static final AntPathMatcher antPathMatcher = new AntPathMatcher();

    private Set<String> permitAll = new ConcurrentHashSet<>();

    private Set<String> ignoreAuthority = new ConcurrentHashSet<>();

    public AccessManager(APIProperties apiProperties) {
        this.apiProperties = apiProperties;
        //TODO: 默认放行
        if(apiProperties!=null){
            if(apiProperties.getPermitAll()!=null){
                permitAll.addAll(apiProperties.getPermitAll());
            }
            if(apiProperties.getDocEnable()){
                Set<String> docSet=new CopyOnWriteArraySet<>();
                docSet.add("/**/v2/api-docs/**");
                docSet.add("/**/v2/api-docs-ext/**");
                docSet.add("/**/swagger-resources/**");
                docSet.add("/webjars/**");
                docSet.add("/doc.html");
                permitAll.addAll(docSet);
            }
        }
    }

    /**
     * 实现权限验证判断
     * @param mono
     * @param authorizationContext
     * @return
     */
    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {
        ServerWebExchange exchange = authorizationContext.getExchange();
        //TODO:请求资源
        String requestPath = exchange.getRequest().getURI().getPath();
        //TODO:是否直接放行
        if (permitAll(requestPath)) {
            return Mono.just(new AuthorizationDecision(true));
        }

        return mono.map(auth -> {
            return new AuthorizationDecision(checkAuthorities(exchange, auth, requestPath));
        }).defaultIfEmpty(new AuthorizationDecision(false));
    }

    /**
     * 始终放行资源校验
     * @param requestPath 请求路径
     * @return
     */
    private boolean permitAll(String requestPath){

        return permitAll.stream()
                .filter(r -> antPathMatcher.match(r, requestPath)).findFirst().isPresent();
    }

    /**
     * 权限校验
     * @param exchange
     * @param auth
     * @param requestPath
     * @return
     */
    private boolean checkAuthorities(ServerWebExchange exchange, Authentication auth, String requestPath) {
        if(auth instanceof OAuth2Authentication){
            OAuth2Authentication auth2Authentication = (OAuth2Authentication) auth;
            String clientId = auth2Authentication.getOAuth2Request().getClientId();
            log.info("clientId is {}",clientId);
        }
        Object principal = auth.getPrincipal();
        if(principal!=null){
           return true;
        }
        return false;
    }
}
