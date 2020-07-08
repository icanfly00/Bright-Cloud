package com.tml.gateway.swagger2;

import com.google.common.collect.Lists;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.List;

/**
 * @Description com.tml.gateway.swagger2
 * @Author TuMingLong
 * @Date 2020/7/8 14:35
 */
public class FastSwagger2GatewayResourceProvider implements SwaggerResourcesProvider {

    private final RouteDefinitionLocator definitionLocator;

    public FastSwagger2GatewayResourceProvider(RouteDefinitionLocator definitionLocator) {
        this.definitionLocator = definitionLocator;
    }

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = Lists.newArrayList();
        definitionLocator.getRouteDefinitions()
                .filter(routeDefinition -> routeDefinition.getUri().toString().contains("lb://"))
                .subscribe(routeDefinition -> {
                    routeDefinition.getPredicates().stream()
                            .filter(predicateDefinition -> ("Path").equalsIgnoreCase(predicateDefinition.getName()))
                            .filter(predicateDefinition -> !predicateDefinition.getArgs().containsKey("_rateLimit"))
                            .forEach(predicateDefinition -> resources.add(swaggerResource(predicateDefinition.getArgs().get("name"),
                                    predicateDefinition.getArgs().get("pattern").replace("/**", "/v2/api-docs"))));
                });
        return null;
    }


    private SwaggerResource swaggerResource(String name, String location) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion("2.0");
        return swaggerResource;
    }
}
