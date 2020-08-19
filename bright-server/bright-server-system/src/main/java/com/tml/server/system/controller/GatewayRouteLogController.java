package com.tml.server.system.controller;

import com.tml.api.system.entity.GatewayRouteLog;
import com.tml.common.core.entity.CommonResult;
import com.tml.common.core.entity.QueryRequest;
import com.tml.common.core.exception.BrightException;
import com.tml.common.core.utils.BrightUtil;
import com.tml.server.system.service.IGatewayRouteLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 网关日志 Controller
 *
 * @author JacksonTu
 * @date 2020-08-13 09:47:06
 */
@Slf4j
@Validated
@RestController
@RequestMapping("gatewayRouteLog")
@RequiredArgsConstructor
public class GatewayRouteLogController {

    private final IGatewayRouteLogService gatewayRouteLogService;


    @GetMapping("list")
    @PreAuthorize("hasAuthority('gatewayRouteLog:list')")
    public CommonResult gatewayRouteLogList(QueryRequest request, GatewayRouteLog gatewayRouteLog) {
        Map<String, Object> dataTable = BrightUtil.getDataTable(this.gatewayRouteLogService.pageGatewayRouteLog(request, gatewayRouteLog));
        return new CommonResult().data(dataTable);
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('gatewayRouteLog:delete')")
    public void deleteGatewayRouteLog(GatewayRouteLog gatewayRouteLog) throws BrightException {
        try {
            this.gatewayRouteLogService.deleteGatewayRouteLog(gatewayRouteLog);
        } catch (Exception e) {
            String message = "删除GatewayRouteLog失败";
            log.error(message, e);
            throw new BrightException(message);
        }
    }
}
