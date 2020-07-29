package com.tml.gateway.swagger2.config;

import com.tml.gateway.service.IGatewayDynamicRouteService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description com.tml.gateway.swagger2.config
 * @Author TuMingLong
 * @Date 2020/7/29 16:37
 */
@Slf4j
@Component
@Primary
@AllArgsConstructor
public class SwaggerResourceConfig implements SwaggerResourcesProvider {

    private final IGatewayDynamicRouteService gatewayDynamicRouteService;
    private final RouteDefinitionLocator routeDefinitionLocator;


    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        List<String> routes = Lists.newArrayList();
        gatewayDynamicRouteService.getRouteList().stream().forEach(routeDefinition -> routes.add(routeDefinition.getId()));
        routeDefinitionLocator.getRouteDefinitions()
                .filter(routeDefinition -> routes.contains(routeDefinition.getId()))
                .subscribe(routeDefinition -> {
                    routeDefinition.getPredicates().stream()
                            .filter(predicateDefinition -> ("Path").equalsIgnoreCase(predicateDefinition.getName()))
                            .forEach(predicateDefinition -> resources.add(swaggerResource(routeDefinition.getId(),
                                    predicateDefinition.getArgs().get("pattern")
                                            .replace("**", "v2/api-docs"))));
                });
        return resources;
    }


    private SwaggerResource swaggerResource(String name, String location) {
        log.info("name:{},location:{}", name, location);
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion("2.0");
        return swaggerResource;
    }
}
