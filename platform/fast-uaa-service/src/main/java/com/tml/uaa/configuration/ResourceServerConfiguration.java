package com.tml.uaa.configuration;

import com.tml.common.security.handler.RestAccessDeniedHandler;
import com.tml.common.security.handler.RestAuthenticationEntryPoint;
import com.tml.common.security.properties.RestSecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Description 资源服务器配置
 * @Author TuMingLong
 * @Date 2020/5/20 17:00
 */
@Slf4j
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Resource
    private RestSecurityProperties properties;

    @Resource
    private TokenStore tokenStore;

    private BearerTokenExtractor tokenExtractor = new BearerTokenExtractor();

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources
                .tokenStore(tokenStore)
                .stateless(true);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        List<String> list = properties.getAnonUris();
        String[] antPatterns = new String[list.size()];
        list.toArray(antPatterns);

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authorizeRequests()
                .antMatchers(antPatterns).permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .successForwardUrl("/")
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .addLogoutHandler(new CookieClearingLogoutHandler("token", "remember-me"))
                .logoutSuccessHandler(new LogoutSuccessHandler())
                .and()
                // 认证鉴权错误处理,为了统一异常处理。每个资源服务器都应该加上
                .exceptionHandling()
                .accessDeniedHandler(new RestAccessDeniedHandler())
                .authenticationEntryPoint(new RestAuthenticationEntryPoint())
                .and()
                .csrf()
                .disable()
                // 禁用httpBasic
                .httpBasic().disable();

    }


    public class LogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {
        public LogoutSuccessHandler() {
            // 重定向到原地址
            this.setUseReferer(true);
        }

        @Override
        public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
            try {
                // 解密请求头
                authentication = tokenExtractor.extract(request);
                if (authentication != null && authentication.getPrincipal() != null) {
                    String tokenValue = authentication.getPrincipal().toString();
                    log.info("revokeToken tokenValue:{}", tokenValue);
                    // 移除token
                    tokenStore.removeAccessToken(tokenStore.readAccessToken(tokenValue));
                }
            } catch (Exception e) {
                log.error("revokeToken error:{}", e);
            }
            super.onLogoutSuccess(request, response, authentication);
        }
    }
}
