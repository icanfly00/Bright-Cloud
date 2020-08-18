package com.tml.server.system.controller;

import com.tml.api.system.entity.GatewayDynamicRoute;
import com.tml.api.system.entity.GatewayRouteLimitRule;
import com.tml.server.system.service.IGatewayRouteLimitRuleService;
import com.tml.common.core.entity.CommonResult;
import com.tml.common.core.entity.QueryRequest;
import com.tml.common.core.exception.BrightException;
import com.tml.common.core.utils.BrightUtil;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * 限流规则 Controller
 *
 * @author JacksonTu
 * @date 2020-08-13 09:47:22
 */
@Slf4j
@Validated
@RestController
@RequestMapping("gatewayRouteLimitRule")
@RequiredArgsConstructor
public class GatewayRouteLimitRuleController {

    private final IGatewayRouteLimitRuleService gatewayRouteLimitRuleService;

    @GetMapping("check")
    public boolean check(@NotBlank(message = "{required}") @RequestParam("requestUri") String requestUri,@NotBlank(message = "{required}") @RequestParam("requestMethod") String requestMethod) {
        boolean flag = this.gatewayRouteLimitRuleService.checkUriAndMethod(requestUri,requestMethod);
        return flag;
    }

    @GetMapping("list")
    @PreAuthorize("hasAuthority('gatewayRouteLimitRule:list')")
    public CommonResult gatewayRouteLimitRuleList(QueryRequest request, GatewayRouteLimitRule gatewayRouteLimitRule) {
        Map<String, Object> dataTable = BrightUtil.getDataTable(this.gatewayRouteLimitRuleService.pageGatewayRouteLimitRule(request, gatewayRouteLimitRule));
        return new CommonResult().data(dataTable);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('gatewayRouteLimitRule:add')")
    public void addGatewayRouteLimitRule(@Valid GatewayRouteLimitRule gatewayRouteLimitRule) throws BrightException {
        try {
            gatewayRouteLimitRule.setCreateTime(LocalDateTime.now());
            gatewayRouteLimitRule.setCreateUserId(BrightUtil.getCurrentUser().getUserId());
            this.gatewayRouteLimitRuleService.saveGatewayRouteLimitRule(gatewayRouteLimitRule);
        } catch (Exception e) {
            String message = "新增GatewayRouteLimitRule失败";
            log.error(message, e);
            throw new BrightException(message);
        }
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('gatewayRouteLimitRule:delete')")
    public void deleteGatewayRouteLimitRule(GatewayRouteLimitRule gatewayRouteLimitRule) throws BrightException {
        try {
            this.gatewayRouteLimitRuleService.deleteGatewayRouteLimitRule(gatewayRouteLimitRule);
        } catch (Exception e) {
            String message = "删除GatewayRouteLimitRule失败";
            log.error(message, e);
            throw new BrightException(message);
        }
    }

    @PutMapping
    @PreAuthorize("hasAuthority('gatewayRouteLimitRule:update')")
    public void updateGatewayRouteLimitRule(GatewayRouteLimitRule gatewayRouteLimitRule) throws BrightException {
        try {
            gatewayRouteLimitRule.setUpdateTime(LocalDateTime.now());
            gatewayRouteLimitRule.setUpdateUserId(BrightUtil.getCurrentUser().getUserId());
            this.gatewayRouteLimitRuleService.updateGatewayRouteLimitRule(gatewayRouteLimitRule);
        } catch (Exception e) {
            String message = "修改GatewayRouteLimitRule失败";
            log.error(message, e);
            throw new BrightException(message);
        }
    }
}
