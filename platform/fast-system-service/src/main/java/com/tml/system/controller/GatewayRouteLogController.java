package com.tml.system.controller;

import com.tml.common.api.CommonResult;
import com.tml.common.web.vo.PageVo;
import com.tml.system.dto.GatewayRouteLogDto;
import com.tml.system.entity.GatewayRouteLog;
import com.tml.system.service.IGatewayRouteLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * @Description 网关日志接口
 * @Author TuMingLong
 * @Date 2020/7/9 17:11
 */
@Api(value = "网关日志接口", tags = "网关日志接口")
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("gateway/routeLog")
public class GatewayRouteLogController {

    private final IGatewayRouteLogService gatewayRouteLogService;

    @ApiOperation(value = "网关日志分页列表", notes = "网关日志分页列表")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('gateway:routeLog:list')")
    public CommonResult<PageVo<GatewayRouteLog>> pageList(GatewayRouteLogDto gatewayRouteLogDto) {
        PageVo<GatewayRouteLog> pageVo = gatewayRouteLogService.pageList(gatewayRouteLogDto);
        return CommonResult.success(pageVo);
    }

    @ApiOperation(value = "删除网关日志", notes = "删除网关日志")
    @ApiImplicitParam(paramType = "query", name = "ids", value = "黑名单ID集合", required = true, dataType = "String")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('gateway:routeLog:delete')")
    public CommonResult deleteGatewayBlackList(String ids) {
        String[] idArray = StringUtils.splitByWholeSeparatorPreserveAllTokens(ids, ",");
        gatewayRouteLogService.removeByIds(Arrays.asList(idArray));
        return CommonResult.success("删除网关日志成功");
    }
}
