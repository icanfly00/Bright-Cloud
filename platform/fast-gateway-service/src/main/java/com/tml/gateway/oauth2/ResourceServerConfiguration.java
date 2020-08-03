package com.tml.gateway.oauth2;


import com.tml.gateway.configuration.APIProperties;
import com.tml.gateway.oauth2.handler.FastAccessDeniedHandler;
import com.tml.gateway.oauth2.handler.FastAuthenticationEntryPoint;
import com.tml.gateway.oauth2.handler.PreRequestFilter;
import com.tml.gateway.oauth2.handler.RouteLogFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.security.oauth2.server.resource.web.server.ServerBearerTokenAuthenticationConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationEntryPointFailureHandler;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.context.SecurityContextServerWebExchange;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * @Description 网关层安全配置
 * @Author TuMingLong
 * @Date 2020/7/29 20:49
 */
@Configuration
@RequiredArgsConstructor
public class ResourceServerConfiguration {

    private static final String MAX_AGE = "18000L";
    private final RedisConnectionFactory redisConnectionFactory;

    private final APIProperties apiProperties;

    private final FastAccessDeniedHandler fastAccessDeniedHandler;

    private final FastAuthenticationEntryPoint fastAuthenticationEntryPoint;

    private final RouteLogFilter routeLogFilter;


    /**
     * 跨域配置
     */
    public WebFilter corsFilter() {
        return (ServerWebExchange ctx, WebFilterChain chain) -> {
            ServerHttpRequest request = ctx.getRequest();
            if (CorsUtils.isCorsRequest(request)) {
                HttpHeaders requestHeaders = request.getHeaders();
                ServerHttpResponse response = ctx.getResponse();
                HttpMethod requestMethod = requestHeaders.getAccessControlRequestMethod();
                HttpHeaders headers = response.getHeaders();
                headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, requestHeaders.getOrigin());
                headers.addAll(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, requestHeaders.getAccessControlRequestHeaders());
                if (requestMethod != null) {
                    headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, requestMethod.name());
                }
                headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
                headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "*");
                headers.add(HttpHeaders.ACCESS_CONTROL_MAX_AGE, MAX_AGE);
                if (request.getMethod() == HttpMethod.OPTIONS) {
                    response.setStatusCode(HttpStatus.OK);
                    return Mono.empty();
                }
            }
            return chain.filter(ctx);
        };
    }

    @Bean
    SecurityWebFilterChain webFluxSecurityFilterChain(ServerHttpSecurity http) throws Exception{
        //token管理器
        ReactiveAuthenticationManager tokenAuthenticationManager = new RedisAuthenticationManager(new RedisTokenStore(redisConnectionFactory));
        //认证过滤器
        AuthenticationWebFilter authenticationWebFilter = new AuthenticationWebFilter(tokenAuthenticationManager);
        authenticationWebFilter.setServerAuthenticationConverter(new ServerBearerTokenAuthenticationConverter());
        authenticationWebFilter.setAuthenticationFailureHandler(new ServerAuthenticationEntryPointFailureHandler(fastAuthenticationEntryPoint));
        authenticationWebFilter.setAuthenticationSuccessHandler(new ServerAuthenticationSuccessHandler() {
            @Override
            public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
                ServerWebExchange exchange = webFilterExchange.getExchange();
                SecurityContextServerWebExchange securityContextServerWebExchange = new SecurityContextServerWebExchange(exchange, ReactiveSecurityContextHolder.getContext().subscriberContext(
                        ReactiveSecurityContextHolder.withAuthentication(authentication)
                ));
                return webFilterExchange.getChain().filter(securityContextServerWebExchange);
            }
        });

        AccessManager accessManager=new AccessManager(apiProperties);
        http
                .httpBasic().disable()
                .csrf().disable()
                .authorizeExchange()
                .pathMatchers("/").permitAll()
                //动态权限验证
                .anyExchange().access(accessManager)
                .and().exceptionHandling()
                .accessDeniedHandler(fastAccessDeniedHandler)
                .authenticationEntryPoint(fastAuthenticationEntryPoint)
                .and()
                // 日志前置过滤器
                .addFilterAt(new PreRequestFilter(), SecurityWebFiltersOrder.FIRST)
                // 跨域过滤器
                .addFilterAt(corsFilter(), SecurityWebFiltersOrder.CORS)
                //oauth2认证过滤器
                .addFilterAt(authenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                // 日志过滤器
                .addFilterAt(routeLogFilter, SecurityWebFiltersOrder.SECURITY_CONTEXT_SERVER_WEB_EXCHANGE);;
        return http.build();
    }
}
