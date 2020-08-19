package com.tml.server.system.controller;

import com.tml.api.system.entity.GatewayDynamicRoute;
import com.tml.common.core.entity.CommonResult;
import com.tml.common.core.entity.QueryRequest;
import com.tml.common.core.exception.BrightException;
import com.tml.common.core.utils.BrightUtil;
import com.tml.server.system.service.IGatewayDynamicRouteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.Map;

/**
 * 动态路由配置表 Controller
 *
 * @author JacksonTu
 * @date 2020-08-13 09:47:27
 */
@Slf4j
@Validated
@RestController
@RequestMapping("gatewayDynamicRoute")
@RequiredArgsConstructor
public class GatewayDynamicRouteController {

    private final IGatewayDynamicRouteService gatewayDynamicRouteService;

    @GetMapping("check/{routeId}")
    public boolean checkRouteId(@NotBlank(message = "{required}") @PathVariable String routeId) {
        boolean flag = this.gatewayDynamicRouteService.checkRouteId(routeId);
        return flag;
    }

    @GetMapping("list")
    @PreAuthorize("hasAuthority('gatewayDynamicRoute:list')")
    public CommonResult gatewayDynamicRouteList(QueryRequest request, GatewayDynamicRoute gatewayDynamicRoute) {
        Map<String, Object> dataTable = BrightUtil.getDataTable(this.gatewayDynamicRouteService.pageGatewayDynamicRoute(request, gatewayDynamicRoute));
        return new CommonResult().data(dataTable);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('gatewayDynamicRoute:add')")
    public void addGatewayDynamicRoute(@Valid GatewayDynamicRoute gatewayDynamicRoute) throws BrightException {
        try {
            gatewayDynamicRoute.setCreateTime(new Date());
            gatewayDynamicRoute.setCreateUserId(BrightUtil.getCurrentUser().getUserId());
            this.gatewayDynamicRouteService.saveGatewayDynamicRoute(gatewayDynamicRoute);
        } catch (Exception e) {
            String message = "新增GatewayDynamicRoute失败";
            log.error(message, e);
            throw new BrightException(message);
        }
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('gatewayDynamicRoute:delete')")
    public void deleteGatewayDynamicRoute(GatewayDynamicRoute gatewayDynamicRoute) throws BrightException {
        try {
            this.gatewayDynamicRouteService.deleteGatewayDynamicRoute(gatewayDynamicRoute);
        } catch (Exception e) {
            String message = "删除GatewayDynamicRoute失败";
            log.error(message, e);
            throw new BrightException(message);
        }
    }

    @PutMapping
    @PreAuthorize("hasAuthority('gatewayDynamicRoute:update')")
    public void updateGatewayDynamicRoute(GatewayDynamicRoute gatewayDynamicRoute) throws BrightException {
        try {
            gatewayDynamicRoute.setUpdateTime(new Date());
            gatewayDynamicRoute.setUpdateUserId(BrightUtil.getCurrentUser().getUserId());
            this.gatewayDynamicRouteService.updateGatewayDynamicRoute(gatewayDynamicRoute);
        } catch (Exception e) {
            String message = "修改GatewayDynamicRoute失败";
            log.error(message, e);
            throw new BrightException(message);
        }
    }
}
