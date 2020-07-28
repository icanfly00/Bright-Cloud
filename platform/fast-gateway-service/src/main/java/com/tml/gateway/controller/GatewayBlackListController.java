package com.tml.gateway.controller;

import com.tml.common.api.CommonResult;
import com.tml.common.web.vo.PageVo;
import com.tml.gateway.dto.GatewayBlackListDto;
import com.tml.gateway.entity.GatewayBlackList;
import com.tml.gateway.service.IGatewayBlackListService;
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
 * @Description 黑名单接口
 * @Author TuMingLong
 * @Date 2020/7/9 15:45
 */
@Api(value = "黑名单接口", tags = "黑名单接口")
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("gateway/blackList")
public class GatewayBlackListController {

    private final IGatewayBlackListService gatewayBlackListService;

    @ApiOperation(value = "黑名单分页列表", notes = "黑名单分页列表")
    @GetMapping("/list")
    //@PreAuthorize("hasAuthority('gateway:blackList:list')")
    public CommonResult<PageVo<GatewayBlackList>> pageList(GatewayBlackListDto gatewayBlackListDto) {
        PageVo<GatewayBlackList> pageVo = gatewayBlackListService.pageList(gatewayBlackListDto);
        return CommonResult.success(pageVo);
    }

    @ApiOperation(value = "根据参数查找黑名单是否存在", notes = "根据参数查找黑名单是否存在")
    @GetMapping("/findByCondition")
    //@PreAuthorize("hasAuthority('gateway:blackList:findByCondition')")
    public CommonResult<GatewayBlackList> findByCondition(GatewayBlackListDto gatewayBlackListDto) {
        return CommonResult.success(gatewayBlackListService.findByCondition(gatewayBlackListDto));
    }

    @ApiOperation(value = "新增黑名单", notes = "新增黑名单")
    @PostMapping("/add")
    //@PreAuthorize("hasAuthority('gateway:blackList:add')")
    public CommonResult addGatewayBlackList(@Valid GatewayBlackList gatewayBlackList) {
        gatewayBlackListService.saveGatewayBlackList(gatewayBlackList);
        return CommonResult.success("新增黑名单成功");
    }

    @ApiOperation(value = "更新黑名单", notes = "更新黑名单")
    @PostMapping("/update")
    //@PreAuthorize("hasAuthority('gateway:blackList:update')")
    public CommonResult updateGatewayBlackList(@Valid GatewayBlackList gatewayBlackList) {
        gatewayBlackListService.updateGatewayBlackList(gatewayBlackList);
        return CommonResult.success("更新黑名单成功");
    }


    @ApiOperation(value = "删除黑名单", notes = "删除黑名单")
    @ApiImplicitParam(paramType = "query", name = "ids", value = "黑名单ID集合", required = true, dataType = "String")
    @PostMapping("/delete")
    //@PreAuthorize("hasAuthority('gateway:blackList:delete')")
    public CommonResult deleteGatewayBlackList(String ids) {
        String[] idArray = StringUtils.splitByWholeSeparatorPreserveAllTokens(ids, ",");
        gatewayBlackListService.deleteGatewayBlackList(Arrays.asList(idArray));
        return CommonResult.success("删除黑名单成功");
    }
}
