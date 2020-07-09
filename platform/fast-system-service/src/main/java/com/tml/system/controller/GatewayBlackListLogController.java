package com.tml.system.controller;

import com.tml.common.api.CommonResult;
import com.tml.common.web.vo.PageVo;
import com.tml.system.dto.GatewayBlackListLogDto;
import com.tml.system.entity.GatewayBlackListLog;
import com.tml.system.service.IGatewayBlackListLogService;
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
 * @Description 黑名单日志接口
 * @Author TuMingLong
 * @Date 2020/7/9 16:26
 */
@Api(value = "黑名单日志接口", tags = "黑名单接口")
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("gateway/blackListLog")
public class GatewayBlackListLogController {

    private final IGatewayBlackListLogService gatewayBlackListLogService;

    @ApiOperation(value = "黑名单日志分页列表", notes = "黑名单日志分页列表")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('gateway:blackListLog:list')")
    public CommonResult<PageVo<GatewayBlackListLog>> pageList(GatewayBlackListLogDto gatewayBlackListLogDto) {
        PageVo<GatewayBlackListLog> pageVo = gatewayBlackListLogService.pageList(gatewayBlackListLogDto);
        return CommonResult.success(pageVo);
    }

    @ApiOperation(value = "删除黑名单日志", notes = "删除黑名单日志")
    @ApiImplicitParam(paramType = "query", name = "ids", value = "黑名单ID集合", required = true, dataType = "String")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('gateway:blackListLog:delete')")
    public CommonResult deleteGatewayBlackList(String ids) {
        String[] idArray = StringUtils.splitByWholeSeparatorPreserveAllTokens(ids, ",");
        gatewayBlackListLogService.removeByIds(Arrays.asList(idArray));
        return CommonResult.success("删除黑名单日志成功");
    }
}
