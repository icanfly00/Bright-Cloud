package com.tml.system.controller;


import com.tml.common.api.CommonResult;
import com.tml.common.exception.APIException;
import com.tml.common.web.vo.PageVo;
import com.tml.system.dto.OauthClientDetailsDto;
import com.tml.system.entity.OauthClientDetails;
import com.tml.system.service.IOauthClientDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;


/**
 * @Description OAuth2.0客户端信息 控制层
 * @Author TuMingLong
 * @Date 2020/5/10 16:12
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("client")
public class OauthClientDetailsController {

    private final IOauthClientDetailsService oauthClientDetailsService;

    @GetMapping("check/{clientId}")
    public boolean checkUserName(@NotBlank(message = "{required}") @PathVariable String clientId) {
        OauthClientDetails client = this.oauthClientDetailsService.findById(clientId);
        return client == null;
    }

    @GetMapping("secret/{clientId}")
    @PreAuthorize("hasAuthority('client:decrypt')")
    public CommonResult getOriginClientSecret(@NotBlank(message = "{required}") @PathVariable String clientId) {
        OauthClientDetails client = this.oauthClientDetailsService.findById(clientId);
        String origin = client != null ? client.getOriginSecret() : StringUtils.EMPTY;
        return CommonResult.success(origin);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('client:view')")
    public CommonResult oauthClientDetailsList(OauthClientDetailsDto oauthClientDetailsDto) {
        PageVo pageVo= this.oauthClientDetailsService.pageList(oauthClientDetailsDto);
        return CommonResult.success(pageVo);
    }


    @PostMapping
    @PreAuthorize("hasAuthority('client:add')")
    public CommonResult addOauthClientDetails(@Valid OauthClientDetails oAuthClientDetails) throws APIException {
        try {
            this.oauthClientDetailsService.createOauthClientDetails(oAuthClientDetails);
            return CommonResult.success("新增客户端成功");
        } catch (Exception e) {
            String message = "新增客户端失败";
            log.error(message, e);
            throw new APIException(message);
        }
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('client:delete')")
    public CommonResult deleteOauthClientDetails(@NotBlank(message = "{required}") String clientIds) throws APIException {
        try {
            this.oauthClientDetailsService.deleteOauthClientDetails(clientIds);
            return CommonResult.success("删除客户端成功");
        } catch (Exception e) {
            String message = "删除客户端失败";
            log.error(message, e);
            throw new APIException(message);
        }
    }

    @PutMapping
    @PreAuthorize("hasAuthority('client:update')")
    public CommonResult updateOauthClientDetails(@Valid OauthClientDetails oAuthClientDetails) throws APIException {
        try {
            this.oauthClientDetailsService.updateOauthClientDetails(oAuthClientDetails);
            return CommonResult.success("修改客户端成功");
        } catch (Exception e) {
            String message = "修改客户端失败";
            log.error(message, e);
            throw new APIException(message);
        }
    }
}
