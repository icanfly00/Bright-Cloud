package com.tml.server.system.controller;

import com.tml.api.system.entity.GatewayRouteLimitRuleLog;
import com.tml.common.core.entity.CommonResult;
import com.tml.common.core.entity.QueryRequest;
import com.tml.common.core.exception.BrightException;
import com.tml.common.core.utils.BrightUtil;
import com.tml.server.system.service.IGatewayRouteLimitRuleLogService;
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
 * 限流规则日志 Controller
 *
 * @author JacksonTu
 * @date 2020-08-13 09:47:12
 */
@Slf4j
@Validated
@RestController
@RequestMapping("gatewayRouteLimitRuleLog")
@RequiredArgsConstructor
public class GatewayRouteLimitRuleLogController {

    private final IGatewayRouteLimitRuleLogService gatewayRouteLimitRuleLogService;

    @GetMapping("list")
    @PreAuthorize("hasAuthority('gatewayRouteLimitRuleLog:list')")
    public CommonResult gatewayRouteLimitRuleLogList(QueryRequest request, GatewayRouteLimitRuleLog gatewayRouteLimitRuleLog) {
        Map<String, Object> dataTable = BrightUtil.getDataTable(this.gatewayRouteLimitRuleLogService.pageGatewayRouteLimitRuleLog(request, gatewayRouteLimitRuleLog));
        return new CommonResult().data(dataTable);
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('gatewayRouteLimitRuleLog:delete')")
    public void deleteGatewayRouteLimitRuleLog(GatewayRouteLimitRuleLog gatewayRouteLimitRuleLog) throws BrightException {
        try {
            this.gatewayRouteLimitRuleLogService.deleteGatewayRouteLimitRuleLog(gatewayRouteLimitRuleLog);
        } catch (Exception e) {
            String message = "删除GatewayRouteLimitRuleLog失败";
            log.error(message, e);
            throw new BrightException(message);
        }
    }
}
