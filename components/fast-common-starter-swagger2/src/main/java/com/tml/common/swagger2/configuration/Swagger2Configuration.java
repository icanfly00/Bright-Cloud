package com.tml.common.swagger2.configuration;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;;
import com.google.common.collect.Lists;
import com.tml.common.swagger2.properties.Swagger2Properties;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

/**
 * @Description Swagger2配置
 * @Author TuMingLong
 * @Date 2020/4/6 15:54
 */
@Slf4j
@EnableSwagger2
@EnableKnife4j
@EnableConfigurationProperties(Swagger2Properties.class)
@ConditionalOnProperty(prefix = "hdw.swagger2", name = "enabled", havingValue = "true")
public class Swagger2Configuration {

    private final Swagger2Properties swagger2Properties;

    public Swagger2Configuration(Swagger2Properties swagger2Properties) {
        this.swagger2Properties = swagger2Properties;
    }

    private static final String SCOPE_PREFIX = "scope.";

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build()
                .securityContexts(Lists.newArrayList(securityContext()))
                .securitySchemes(Lists.<SecurityScheme>newArrayList(apiKey()));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // 文档标题
                .title(swagger2Properties.getTitle())
                // 文档描述
                .description(swagger2Properties.getDescription())
                .version(swagger2Properties.getVersion())
                .license("MIT 协议")
                .licenseUrl("http://www.opensource.org/licenses/MIT")
                .build();
    }


    private ApiKey apiKey() {
        return new ApiKey("Bearer Token", "Authorization", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex("/.*"))
                .build();
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Lists.newArrayList(new SecurityReference("Bearer Token", authorizationScopes));

    }
}
