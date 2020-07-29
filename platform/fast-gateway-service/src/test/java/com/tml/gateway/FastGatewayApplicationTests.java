package com.tml.gateway;

import com.tml.gateway.dto.GatewayBlackListDto;
import com.tml.gateway.dto.GatewayRouteLimitRuleDto;
import com.tml.gateway.entity.GatewayBlackList;
import com.tml.gateway.entity.GatewayDynamicRoute;
import com.tml.gateway.entity.GatewayRouteLimitRule;
import com.tml.gateway.service.IGatewayBlackListService;
import com.tml.gateway.service.IGatewayDynamicRouteService;
import com.tml.gateway.service.IGatewayRouteLimitRuleService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FastGatewayApplicationTests {

    @Resource
    private IGatewayDynamicRouteService gatewayDynamicRouteService;

    @Resource
    private IGatewayBlackListService gatewayBlackListService;

    @Resource
    private IGatewayRouteLimitRuleService gatewayRouteLimitRuleService;

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

    @Test
    public void addGatewayBlackListTest(){
        GatewayBlackList blackList=new GatewayBlackList();
        blackList.setIp("127.0.0.1");
        blackList.setRequestUri("/**/actuator/**");
        blackList.setRequestMethod("All");
        blackList.setLimitFrom(LocalDateTime.of(2020,7,30,0,30,00));
        blackList.setLimitTo(LocalDateTime.of(2020,7,30,23,59,59));
        blackList.setStatus(1);
        blackList.setCreateTime(LocalDateTime.now());
        blackList.setUpdateTime(LocalDateTime.now());
        blackList.setCreateUserId(1);
        blackList.setUpdateUserId(1);
        gatewayBlackListService.saveGatewayBlackList(blackList);

        GatewayBlackList blackList2=new GatewayBlackList();
        blackList2.setIp("192.168.0.106");
        blackList2.setRequestUri("/**/actuator/**");
        blackList2.setRequestMethod("All");
        blackList2.setLimitFrom(LocalDateTime.of(2020,7,30,0,30,00));
        blackList2.setLimitTo(LocalDateTime.of(2020,7,30,23,59,59));
        blackList2.setStatus(1);
        blackList2.setCreateTime(LocalDateTime.now());
        blackList2.setUpdateTime(LocalDateTime.now());
        blackList2.setCreateUserId(1);
        blackList2.setUpdateUserId(1);
        gatewayBlackListService.saveGatewayBlackList(blackList2);

        GatewayBlackListDto dto=new GatewayBlackListDto();
        dto.setIp("127.0.0.1");
        dto.setStatus(1);

        List<GatewayBlackList> list=gatewayBlackListService.findByCondition(dto);
        if(list!=null && list.size()>0){
            list.stream().forEach(gatewayBlackList -> {
                System.out.println(gatewayBlackList.toString());
            });
        }
    }

    @Test
    public void findAllBackListTest(){
       List<GatewayBlackList> list=gatewayBlackListService.findAllBackList();
       if(list!=null && list.size()>0){
           list.stream().forEach(gatewayBlackList -> {
               System.out.println(gatewayBlackList.toString());
           });
       }
    }

    @Test
    public void addGatewayRouteLimitTest(){
        GatewayRouteLimitRule routeLimitRule=new GatewayRouteLimitRule();
        routeLimitRule.setRequestUri("/auth/**");
        routeLimitRule.setRequestMethod("GET");
        routeLimitRule.setLimitFrom(LocalDateTime.of(2020,7,30,0,30,00));
        routeLimitRule.setLimitTo(LocalDateTime.of(2020,7,30,23,59,59));
        routeLimitRule.setStatus(1);
        routeLimitRule.setCreateTime(LocalDateTime.now());
        routeLimitRule.setUpdateTime(LocalDateTime.now());
        routeLimitRule.setCount(3);
        routeLimitRule.setIntervalSec(10l);
        routeLimitRule.setCreateUserId(1);
        routeLimitRule.setUpdateUserId(1);
        gatewayRouteLimitRuleService.saveGatewayRouteLimitRule(routeLimitRule);

        GatewayRouteLimitRule routeLimitRule2=new GatewayRouteLimitRule();
        routeLimitRule2.setRequestUri("/gateway/**");
        routeLimitRule2.setRequestMethod("GET");
        routeLimitRule2.setLimitFrom(LocalDateTime.of(2020,7,30,0,30,00));
        routeLimitRule2.setLimitTo(LocalDateTime.of(2020,7,30,23,59,59));
        routeLimitRule2.setStatus(1);
        routeLimitRule2.setCreateTime(LocalDateTime.now());
        routeLimitRule2.setUpdateTime(LocalDateTime.now());
        routeLimitRule2.setCount(3);
        routeLimitRule2.setIntervalSec(10l);
        routeLimitRule2.setCreateUserId(1);
        routeLimitRule2.setUpdateUserId(1);
        gatewayRouteLimitRuleService.saveGatewayRouteLimitRule(routeLimitRule2);

        GatewayRouteLimitRuleDto dto=new GatewayRouteLimitRuleDto();
        dto.setRequestUri("/auth/**");
        dto.setRequestMethod("GET");
        dto.setStatus(1);
        GatewayRouteLimitRule gatewayRouteLimitRule=gatewayRouteLimitRuleService.findByCondition(dto);
        System.out.println(gatewayRouteLimitRule.toString());
    }

    @Test
    public void findAllGatewayRouteLimitRuleTest(){
        List<GatewayRouteLimitRule> list=gatewayRouteLimitRuleService.findAllRouteLimitRule();
        if(list!=null && list.size()>0){
            list.stream().forEach(gatewayRouteLimitRule -> {
                System.out.println(gatewayRouteLimitRule.toString());
            });
        }
    }
}
