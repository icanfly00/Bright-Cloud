package com.tml.system.controller;

import com.tml.common.api.CommonResult;
import com.tml.common.web.vo.PageVo;
import com.tml.system.dto.GatewayRouteDto;
import com.tml.system.entity.GatewayRoute;
import com.tml.system.service.IGatewayRouteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Description 动态网关接口
 * @Author TuMingLong
 * @Date 2020/7/9 17:26
 */
@Api(value = "动态网关接口", tags = "动态网关接口")
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("gateway/route")
public class GatewayRouteController {

    private final IGatewayRouteService gatewayRouteService;

    @ApiOperation(value = "动态网关分页列表", notes = "动态网关分页列表")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('gateway:route:list')")
    public CommonResult<PageVo<GatewayRoute>> pageList(GatewayRouteDto gatewayRouteDto) {
        PageVo<GatewayRoute> pageVo = gatewayRouteService.pageList(gatewayRouteDto);
        return CommonResult.success(pageVo);
    }

    @ApiOperation(value = "获取路由信息", notes = "获取路由信息")
    @ApiImplicitParam(paramType = "path", name = "ids", value = "网关ID集合", required = true, dataType = "String")
    @GetMapping("/info/{routeId}")
    @PreAuthorize("hasAuthority('gateway:route:info')")
    public CommonResult<GatewayRoute> info(@PathVariable("routeId") Long routeId) {
        return CommonResult.success(gatewayRouteService.getRoute(routeId));
    }

    @ApiOperation(value = "新增网关", notes = "新增网关")
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('gateway:route:add')")
    public CommonResult addGatewayRoute(@Valid GatewayRoute gatewayRoute) {
        gatewayRouteService.addRoute(gatewayRoute);
        return CommonResult.success("新增网关成功");
    }

    @ApiOperation(value = "更新网关", notes = "更新网关")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('gateway:route:update')")
    public CommonResult updateGatewayRoute(@Valid GatewayRoute gatewayRoute) {
        gatewayRouteService.updateRoute(gatewayRoute);
        return CommonResult.success("更新网关成功");
    }

    @ApiOperation(value = "删除网关", notes = "删除网关")
    @ApiImplicitParam(paramType = "query", name = "ids", value = "网关ID集合", required = true, dataType = "String")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('gateway:route:delete')")
    public CommonResult deleteGatewayRoute(Long routeId) {
        gatewayRouteService.removeRoute(routeId);
        return CommonResult.success("删除网关成功");
    }
}
