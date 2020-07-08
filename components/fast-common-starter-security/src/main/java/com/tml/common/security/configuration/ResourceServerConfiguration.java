package com.tml.common.security.configuration;

import com.tml.common.security.handler.RestAccessDeniedHandler;
import com.tml.common.security.handler.RestAuthenticationEntryPoint;
import com.tml.common.security.properties.RestSecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description 资源服务器配置
 * @Author TuMingLong
 * @Date 2020/5/20 17:38
 */
@Slf4j
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
    @Resource
    private RestSecurityProperties properties;
    @Resource
    private RestAccessDeniedHandler accessDeniedHandler;
    @Resource
    private RestAuthenticationEntryPoint authenticationEntryPoint;


    /**
     * 密码编码器
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * 令牌存储策略
     *
     * @return
     */
    @Resource
    private TokenStore tokenStore;

    /**
     * 令牌存储策略
     *
     * @return
     */
    @Resource
    private ResourceServerTokenServices resourceServerTokenServices;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources
                .tokenStore(tokenStore)
                .tokenServices(resourceServerTokenServices)
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
                // fegin访问无需身份认证
                .antMatchers(antPatterns).permitAll()
                .anyRequest().authenticated()
                .and()
                //认证鉴权错误处理,为了统一异常处理。每个资源服务器都应该加上。
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .csrf().disable();
    }
}
