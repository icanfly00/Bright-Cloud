package com.tml.system.controller;

import com.tml.common.api.CommonResult;
import com.tml.common.web.vo.PageVo;
import com.tml.system.dto.GatewayRateLimitRuleDto;
import com.tml.system.entity.GatewayRateLimitRule;
import com.tml.system.service.IGatewayRateLimitRuleService;
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

import javax.validation.Valid;
import java.util.Arrays;

/**
 * @Description 限流规则接口
 * @Author TuMingLong
 * @Date 2020/7/9 16:38
 */
@Api(value = "限流规则接口", tags = "限流规则接口")
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("gateway/rateLimitRule")
public class GatewayRateLimitRuleController {

    private final IGatewayRateLimitRuleService gatewayRateLimitRuleService;

    @ApiOperation(value = "限流规则分页列表", notes = "限流规则分页列表")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('gateway:rateLimitRule:list')")
    public CommonResult<PageVo<GatewayRateLimitRule>> pageList(GatewayRateLimitRuleDto gatewayRateLimitRuleDto) {
        PageVo<GatewayRateLimitRule> pageVo = gatewayRateLimitRuleService.pageList(gatewayRateLimitRuleDto);
        return CommonResult.success(pageVo);
    }

    @ApiOperation(value = "根据参数查找限流规则是否存在", notes = "根据参数查找限流规则是否存在")
    @GetMapping("/findByCondition")
    @PreAuthorize("hasAuthority('gateway:rateLimitRule:findByCondition')")
    public CommonResult<GatewayRateLimitRule> findByCondition(GatewayRateLimitRuleDto gatewayRateLimitRuleDto) {
        return CommonResult.success(gatewayRateLimitRuleService.findByCondition(gatewayRateLimitRuleDto));
    }

    @ApiOperation(value = "新增限流规则", notes = "新增限流规则")
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('gateway:rateLimitRule:add')")
    public CommonResult addGatewayRateLimitRule(@Valid GatewayRateLimitRule gatewayBlackList) {
        gatewayRateLimitRuleService.save(gatewayBlackList);
        return CommonResult.success("新增限流规则成功");
    }

    @ApiOperation(value = "更新限流规则", notes = "更新限流规则")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('gateway:rateLimitRule:update')")
    public CommonResult updateGatewayRateLimitRule(@Valid GatewayRateLimitRule gatewayBlackList) {
        gatewayRateLimitRuleService.updateById(gatewayBlackList);
        return CommonResult.success("更新限流规则成功");
    }


    @ApiOperation(value = "删除限流规则", notes = "删除限流规则")
    @ApiImplicitParam(paramType = "query", name = "ids", value = "限流规则ID集合", required = true, dataType = "String")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('gateway:rateLimitRule:delete')")
    public CommonResult deleteGatewayRateLimitRule(String ids) {
        String[] idArray = StringUtils.splitByWholeSeparatorPreserveAllTokens(ids, ",");
        gatewayRateLimitRuleService.removeByIds(Arrays.asList(idArray));
        return CommonResult.success("删除限流规则成功");
    }
}
