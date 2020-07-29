package com.tml.gateway.controller;

import com.tml.common.api.CommonResult;
import com.tml.common.web.vo.PageVo;
import com.tml.gateway.dto.GatewayDynamicRouteDto;
import com.tml.gateway.entity.GatewayDynamicRoute;
import com.tml.gateway.service.IGatewayDynamicRouteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Arrays;

/**
 * @Description 动态路由配置接口
 * @Author TuMingLong
 * @Date 2020/7/28 15:50
 */
@Api(value = "动态路由配置日志接口", tags = "动态路由配置接口")
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("gateway/dynamicRoute")
public class GatewayDynamicRouteController {

    private final IGatewayDynamicRouteService gatewayDynamicRouteService;

    @ApiOperation(value = "动态路由配置分页列表", notes = "动态路由配置分页列表")
    @GetMapping("/list")
    //@PreAuthorize("hasAuthority('gateway:dynamicRoute:list')")
    public CommonResult<PageVo<GatewayDynamicRoute>> pageList(GatewayDynamicRouteDto dynamicRouteDto) {
        PageVo<GatewayDynamicRoute> pageVo = gatewayDynamicRouteService.pageList(dynamicRouteDto);
        return CommonResult.success(pageVo);
    }


    @ApiOperation(value = "新增动态路由配置", notes = "新增动态路由配置")
    @PostMapping("/add")
    //@PreAuthorize("hasAuthority('gateway:dynamicRoute:add')")
    public CommonResult addGatewayDynamicRoute(@Valid GatewayDynamicRoute dynamicRoute) {
        gatewayDynamicRouteService.add(dynamicRoute);
        return CommonResult.success("新增动态路由配置成功");
    }

    @ApiOperation(value = "更新动态路由配置", notes = "更新动态路由配置")
    @PostMapping("/update")
    //@PreAuthorize("hasAuthority('gateway:dynamicRoute:update')")
    public CommonResult updateGatewayDynamicRoute(@Valid GatewayDynamicRoute dynamicRoute) {
        gatewayDynamicRouteService.update(dynamicRoute);
        return CommonResult.success("更新动态路由配置成功");
    }

    @ApiOperation(value = "删除动态路由配置", notes = "删除动态路由配置")
    @ApiImplicitParam(paramType = "query", name = "id", value = "动态路由配置ID", required = true, dataType = "String")
    @PostMapping("/delete")
    //@PreAuthorize("hasAuthority('gateway:dynamicRoute:delete')")
    public CommonResult deleteGatewayDynamicRoute(Long id) {
        gatewayDynamicRouteService.delete(id);
        return CommonResult.success("删除动态路由配置成功");
    }
}
