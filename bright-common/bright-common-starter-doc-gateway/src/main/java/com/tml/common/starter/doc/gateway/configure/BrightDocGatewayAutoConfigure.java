package com.tml.common.starter.doc.gateway.configure;

import com.tml.common.starter.doc.gateway.filter.BrightDocGatewayHeaderFilter;
import com.tml.common.starter.doc.gateway.handler.BrightDocGatewayHandler;
import com.tml.common.starter.doc.gateway.properties.BrightDocGatewayProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger.web.UiConfiguration;

/**
 * @description 
 * @author JacksonTu
 * @since 2020-08-10 20:30
 * @version 1.0
 */
@Configuration
@EnableConfigurationProperties(BrightDocGatewayProperties.class)
@ConditionalOnProperty(value = "bright.doc.gateway.enable", havingValue = "true", matchIfMissing = true)
public class BrightDocGatewayAutoConfigure {

    private final BrightDocGatewayProperties brightDocGatewayProperties;
    private SecurityConfiguration securityConfiguration;
    private UiConfiguration uiConfiguration;

    public BrightDocGatewayAutoConfigure(BrightDocGatewayProperties brightDocGatewayProperties) {
        this.brightDocGatewayProperties = brightDocGatewayProperties;
    }

    @Autowired(required = false)
    public void setSecurityConfiguration(SecurityConfiguration securityConfiguration) {
        this.securityConfiguration = securityConfiguration;
    }

    @Autowired(required = false)
    public void setUiConfiguration(UiConfiguration uiConfiguration) {
        this.uiConfiguration = uiConfiguration;
    }

    @Bean
    public BrightDocGatewayResourceConfigure brightDocGatewayResourceConfigure(RouteLocator routeLocator, GatewayProperties gatewayProperties) {
        return new BrightDocGatewayResourceConfigure(routeLocator, gatewayProperties);
    }

    @Bean
    public BrightDocGatewayHeaderFilter brightDocGatewayHeaderFilter() {
        return new BrightDocGatewayHeaderFilter();
    }

    @Bean
    public BrightDocGatewayHandler brightDocGatewayHandler(SwaggerResourcesProvider swaggerResources) {
        BrightDocGatewayHandler brightDocGatewayHandler = new BrightDocGatewayHandler();
        brightDocGatewayHandler.setSecurityConfiguration(securityConfiguration);
        brightDocGatewayHandler.setUiConfiguration(uiConfiguration);
        brightDocGatewayHandler.setSwaggerResources(swaggerResources);
        brightDocGatewayHandler.setProperties(brightDocGatewayProperties);
        return brightDocGatewayHandler;
    }
}
