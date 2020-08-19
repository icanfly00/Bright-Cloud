package com.tml.server.system.controller;

import com.tml.api.system.entity.GatewayBlockList;
import com.tml.common.core.entity.CommonResult;
import com.tml.common.core.entity.QueryRequest;
import com.tml.common.core.exception.BrightException;
import com.tml.common.core.utils.BrightUtil;
import com.tml.server.system.service.IGatewayBlockListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * 黑名单 Controller
 *
 * @author JacksonTu
 * @date 2020-08-13 09:46:55
 */
@Slf4j
@Validated
@RestController
@RequestMapping("gatewayBlockList")
@RequiredArgsConstructor
public class GatewayBlockListController {

    private final IGatewayBlockListService gatewayBlockListService;

    @GetMapping("check")
    public boolean check(@RequestParam(required = false, value = "ip") String ip, @RequestParam(value = "requestUri") String requestUri, @RequestParam(value = "requestMethod") String requestMethod) {
        boolean flag = this.gatewayBlockListService.check(ip, requestUri, requestMethod);
        return flag;
    }

    @GetMapping("list")
    @PreAuthorize("hasAuthority('gatewayBlockList:list')")
    public CommonResult gatewayBlockListList(QueryRequest request, GatewayBlockList gatewayBlockList) {
        Map<String, Object> dataTable = BrightUtil.getDataTable(this.gatewayBlockListService.pageGatewayBlockList(request, gatewayBlockList));
        return new CommonResult().data(dataTable);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('gatewayBlockList:add')")
    public void addGatewayBlackList(@Valid GatewayBlockList gatewayBlockList) throws BrightException {
        try {
            gatewayBlockList.setCreateTime(LocalDateTime.now());
            gatewayBlockList.setCreateUserId(BrightUtil.getCurrentUser().getUserId());
            this.gatewayBlockListService.saveGatewayBlockList(gatewayBlockList);
        } catch (Exception e) {
            String message = "新增GatewayBlackList失败";
            log.error(message, e);
            throw new BrightException(message);
        }
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('gatewayBlockList:delete')")
    public void deleteGatewayBlackList(GatewayBlockList gatewayBlockList) throws BrightException {
        try {
            this.gatewayBlockListService.deleteGatewayBlockList(gatewayBlockList);
        } catch (Exception e) {
            String message = "删除GatewayBlackList失败";
            log.error(message, e);
            throw new BrightException(message);
        }
    }

    @PutMapping
    @PreAuthorize("hasAuthority('gatewayBlockList:update')")
    public void updateGatewayBlackList(GatewayBlockList gatewayBlockList) throws BrightException {
        try {
            gatewayBlockList.setUpdateTime(LocalDateTime.now());
            gatewayBlockList.setUpdateUserId(BrightUtil.getCurrentUser().getUserId());
            this.gatewayBlockListService.updateGatewayBlockList(gatewayBlockList);
        } catch (Exception e) {
            String message = "修改GatewayBlackList失败";
            log.error(message, e);
            throw new BrightException(message);
        }
    }
}
