package com.tml.monitor.configuration;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @Description WebSecurity配置
 * @Author TuMingLong
 * @Date 2020/7/1 17:00
 */
@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //TODO: 授权对所有静态资源和登录页面的公共页面的访问权限。
                .antMatchers("/assets/**","/login").permitAll()
                //TODO: 对其他请求进行身份验证
                .anyRequest().authenticated()
                .and()
                //TODO: 配置登录和注销
                .formLogin().loginPage("/login").permitAll()
                .and()
                .logout().logoutUrl("/logout").permitAll()
                .and()
                .cors().disable()
                //TODO: 启用HTTP-Basic支持。只是SpringBootAdminClient注册所必须的
                .httpBasic();
        //TODO: 允许iframe嵌套
        http.headers().frameOptions().disable();
    }
}
