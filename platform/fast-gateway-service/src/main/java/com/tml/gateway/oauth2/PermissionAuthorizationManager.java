package com.tml.gateway.oauth2;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.ConcurrentHashSet;
import com.tml.common.entity.RestUserDetails;
import com.tml.gateway.configuration.APIProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @Description com.tml.gateway.oauth2
 * @Author TuMingLong
 * @Date 16:07
 */
@Slf4j
public class PermissionAuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    private APIProperties apiProperties;

    private static final AntPathMatcher antPathMatcher = new AntPathMatcher();

    private Set<String> permitAll = new ConcurrentHashSet<>();

    private Set<String> ignoreAuthority = new ConcurrentHashSet<>();

    public PermissionAuthorizationManager(APIProperties apiProperties) {
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
     * 始终放行资源校验
     * @param requestPath 请求路径
     * @return
     */
    private boolean permitAll(String requestPath){

        return permitAll.stream()
                .filter(r -> antPathMatcher.match(r, requestPath)).findFirst().isPresent();
    }


    /**
     * 实现权限验证判断
     */
    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authenticationMono, AuthorizationContext authorizationContext) {
        ServerWebExchange exchange = authorizationContext.getExchange();
        //TODO:请求资源
        String requestPath = exchange.getRequest().getURI().getPath();
        //TODO:是否直接放行
        if (permitAll(requestPath)) {
            return Mono.just(new AuthorizationDecision(true));
        }
        return authenticationMono
                .map(auth -> new AuthorizationDecision(checkAuthorities(exchange, auth, requestPath)))
                .defaultIfEmpty(new AuthorizationDecision(false));

    }

    //权限校验
    private boolean checkAuthorities(ServerWebExchange exchange, Authentication authentication, String requestPath) {
        log.info("访问的URL是：{}用户信息:{}", requestPath, authentication.getPrincipal());
        if (authentication.getPrincipal() instanceof RestUserDetails) {
            return (RestUserDetails) authentication.getPrincipal()!=null;
        } else if (authentication.getPrincipal() instanceof Map) {
            return BeanUtil.mapToBeanIgnoreCase((Map) authentication.getPrincipal(), RestUserDetails.class, false)!=null;
        }else {
            return false;
        }
    }
}
