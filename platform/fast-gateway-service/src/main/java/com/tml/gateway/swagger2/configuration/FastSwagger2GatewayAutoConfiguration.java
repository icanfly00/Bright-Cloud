package com.tml.gateway.swagger2.configuration;

import com.tml.gateway.swagger2.FastSwagger2GatewayResourceProvider;
import com.tml.gateway.swagger2.filter.FastSwagger2GatewayHeaderFilter;
import com.tml.gateway.swagger2.handler.FastSwagger2GatewayHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger.web.UiConfiguration;

/**
 * @Description com.tml.gateway.swagger2.configuration
 * @Author TuMingLong
 * @Date 2020/7/8 15:38
 */
//@Configuration
public class FastSwagger2GatewayAutoConfiguration {

    private SecurityConfiguration securityConfiguration;

    private UiConfiguration uiConfiguration;

    @Autowired(required = false)
    public void setSecurityConfiguration(SecurityConfiguration securityConfiguration) {
        this.securityConfiguration = securityConfiguration;
    }

    @Autowired(required = false)
    public void setUiConfiguration(UiConfiguration uiConfiguration) {
        this.uiConfiguration = uiConfiguration;
    }

    @Bean
    public FastSwagger2GatewayResourceProvider fastSwagger2GatewayResourceProvider(RouteDefinitionLocator routeDefinitionLocator) {
        return new FastSwagger2GatewayResourceProvider(routeDefinitionLocator);
    }

    @Bean
    public FastSwagger2GatewayHeaderFilter fastSwagger2GatewayHeaderFilter() {
        return new FastSwagger2GatewayHeaderFilter();
    }

    @Bean
    public FastSwagger2GatewayHandler fastSwagger2GatewayHandler(SwaggerResourcesProvider swaggerResources) {
        FastSwagger2GatewayHandler fastSwagger2GatewayHandler = new FastSwagger2GatewayHandler();
        fastSwagger2GatewayHandler.setSecurityConfiguration(securityConfiguration);
        fastSwagger2GatewayHandler.setUiConfiguration(uiConfiguration);
        fastSwagger2GatewayHandler.setSwaggerResources(swaggerResources);
        return fastSwagger2GatewayHandler;
    }
}
