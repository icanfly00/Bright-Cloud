package com.tml.gateway.controller;

import com.tml.common.api.CommonResult;
import com.tml.common.web.vo.PageVo;
import com.tml.gateway.dto.GatewayRouteLimitLogDto;
import com.tml.gateway.entity.GatewayRouteLimitLog;
import com.tml.gateway.service.IGatewayRouteLimitLogService;
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
import java.util.Arrays;

/**
 * @Description 限流日志接口
 * @Author TuMingLong
 * @Date 2020/7/9 16:52
 */
@Api(value = "限流日志接口", tags = "限流日志接口")
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("gateway/routeLimitLog")
public class GatewayRouteLimitLogController {

    private final IGatewayRouteLimitLogService gatewayRateLimitLogService;

    @ApiOperation(value = "限流日志分页列表", notes = "限流日志分页列表")
    @GetMapping("/list")
    //@PreAuthorize("hasAuthority('gateway:routeLimitLog:list')")
    public CommonResult<PageVo<GatewayRouteLimitLog>> pageList(GatewayRouteLimitLogDto gatewayRouteLimitLogDto) {
        PageVo<GatewayRouteLimitLog> pageVo = gatewayRateLimitLogService.pageList(gatewayRouteLimitLogDto);
        return CommonResult.success(pageVo);
    }

    @ApiOperation(value = "删除限流日志", notes = "删除限流日志")
    @ApiImplicitParam(paramType = "query", name = "ids", value = "黑名单ID集合", required = true, dataType = "String")
    @PostMapping("/delete")
    //@PreAuthorize("hasAuthority('gateway:routeLimitLog:delete')")
    public CommonResult deleteGatewayBlackList(String ids) {
        String[] idArray = StringUtils.splitByWholeSeparatorPreserveAllTokens(ids, ",");
        gatewayRateLimitLogService.removeByIds(Arrays.asList(idArray));
        return CommonResult.success("删除限流日志成功");
    }
}
