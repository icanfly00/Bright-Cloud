package com.tml.uaa.configuration;

import com.tml.common.security.handler.RestOAuth2WebResponseExceptionTranslator;
import com.tml.uaa.service.RedisClientDetailsService;
import com.tml.uaa.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @Description 授权服务器配置
 * @Author TuMingLong
 * @Date 2020/5/20 17:00
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    @Resource
    private DataSource dataSource;

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private UserDetailsServiceImpl userDetailsService;

    /**
     * 授权码 设置授权码模式的授权码如何存取
     *
     * @return
     */
    @Bean
    public AuthorizationCodeServices authorizationCodeServices() {
        return new JdbcAuthorizationCodeServices(dataSource);
    }

    /**
     * 授权store
     *
     * @return
     */
    @Bean
    public ApprovalStore approvalStore() {
        return new JdbcApprovalStore(dataSource);
    }

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
     * 存放客户端详情
     *
     * @return
     */
    @Bean
    public JdbcClientDetailsService clientDetailsService() {
        JdbcClientDetailsService jdbcClientDetailsService = new RedisClientDetailsService(dataSource);
        jdbcClientDetailsService.setPasswordEncoder(passwordEncoder());
        return jdbcClientDetailsService;
    }

    /**
     * 令牌存储策略
     */
    @Resource
    private TokenStore tokenStore;

    @Resource
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    /**
     * 令牌管理服务
     *
     * @return
     */
    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        //客户端信息服务
        tokenServices.setClientDetailsService(clientDetailsService());
        //是否刷新令牌
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setReuseRefreshToken(true);
        tokenServices.setTokenStore(tokenStore);

        //令牌增强
        tokenServices.setTokenEnhancer(jwtAccessTokenConverter);

        //令牌默认有效期2小时
        tokenServices.setAccessTokenValiditySeconds(2 * 60 * 60);
        //刷新令牌默认有效期3天
        tokenServices.setRefreshTokenValiditySeconds(3 * 24 * 60 * 60);
        return tokenServices;
    }


    /**
     * 令牌访问端点安全策略
     * AuthorizationServerSecurityConfigurer 用来配置令牌端点的安全约束
     *
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .tokenKeyAccess("permitAll()") //oauth/token_key公开
                .checkTokenAccess("permitAll()") //开启oauth/check_token验证端口认证权限访问
                // .checkTokenAccess("isAuthenticated()")
                .allowFormAuthenticationForClients();//表单认证
    }

    /**
     * ClientDetailsServiceConfigurer 用来配置客户端详情服务
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService());
    }

    /**
     * 令牌访问端点
     * AuthorizationServerSecurityConfigurer 用来配置令牌（token）的访问端点和令牌服务
     * /oauth/authorize 授权端点
     * /oauth/token 令牌端点
     * /oauth/confirm_access 用户确认授权提交端点
     * /oauth/error 授权服务错误信息端点
     * /oauth/check_token 用于资源服务访问的令牌解析端点
     * /oauth/token_key 提供公有密钥的端点，如果使用Jwt令牌需要用到
     *
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .authenticationManager(authenticationManager) //认证管理器
                .userDetailsService(userDetailsService) //密码模式
                .authorizationCodeServices(authorizationCodeServices()) //授权码服务
                .approvalStore(approvalStore())
                .tokenServices(tokenServices()) //令牌管理服务
                .accessTokenConverter(jwtAccessTokenConverter)
                .allowedTokenEndpointRequestMethods(HttpMethod.POST, HttpMethod.GET);
        // 自定义异常转换类
        endpoints.exceptionTranslator(new RestOAuth2WebResponseExceptionTranslator());
    }
    /**
     * 授权服务配置总结
     * 既然要完成认证，它首先得知道客户端信息从哪里读取，因此要进行客户端详情配置。
     * 既然要颁发token,那么得定义token相关endpoint以及token如何存取，以及客户端支持哪些类型的token
     * 既然暴露除了一些endpoint，那对这些endpoint可以定义一些安全上的约束等
     * web安全配置
     *
     */
}
