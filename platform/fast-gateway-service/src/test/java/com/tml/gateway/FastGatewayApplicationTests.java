package com.tml.gateway;

import com.tml.gateway.entity.GatewayDynamicRoute;
import com.tml.gateway.service.IGatewayDynamicRouteService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FastGatewayApplicationTests {

    @Resource
    private IGatewayDynamicRouteService gatewayDynamicRouteService;

    @Test
    public void addGatewayDynamicRouteTest() {
        GatewayDynamicRoute dynamicRoute=new GatewayDynamicRoute();
        dynamicRoute.setRouteId("fast-system-service");
        dynamicRoute.setRouteUri("lb://fast-system-service");
        dynamicRoute.setRouteOrder(1);
        String sysPredicates="[{\"name\":\"Path\",\"args\":{\"pattern\":\"/system/**\"}}]";
        dynamicRoute.setPredicates(sysPredicates);
        dynamicRoute.setEnable(1);
        dynamicRoute.setCreateTime(LocalDateTime.now());
        dynamicRoute.setUpdateTime(LocalDateTime.now());
        dynamicRoute.setCreateUserId(1);
        dynamicRoute.setUpdateUserId(1);
        boolean flag=gatewayDynamicRouteService.save(dynamicRoute);
        Assert.assertTrue("动态路由添加成功",flag);
    }


}
