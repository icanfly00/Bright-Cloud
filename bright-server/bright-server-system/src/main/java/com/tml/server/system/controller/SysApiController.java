package com.tml.server.system.controller;

import com.tml.server.system.entity.SysApi;
import com.tml.server.system.service.ISysApiService;
import com.tml.common.core.entity.CommonResult;
import com.tml.common.core.entity.QueryRequest;
import com.tml.common.core.exception.BrightException;
import com.tml.common.core.utils.BrightUtil;
import com.tml.common.core.entity.constant.StringConstant;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Map;

/**
 * 系统API接口 Controller
 *
 * @author JacksonTu
 * @date 2020-08-20 19:22:24
 */
@Slf4j
@Validated
@RestController
@RequestMapping("sysApi")
@RequiredArgsConstructor
public class SysApiController {

    private final ISysApiService sysApiService;

    @GetMapping
    @PreAuthorize("hasAuthority('sysApi:list')")
    public CommonResult listSysApi(SysApi sysApi) {
        return new CommonResult().data(sysApiService.listSysApi(sysApi));
    }

    @GetMapping("list")
    @PreAuthorize("hasAuthority('sysApi:list')")
    public CommonResult pageSysApi(QueryRequest request, SysApi sysApi) {
        Map<String, Object> dataTable = BrightUtil.getDataTable(this.sysApiService.pageSysApi(request, sysApi));
        return new CommonResult().data(dataTable);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('sysApi:add')")
    public void saveSysApi(@Valid SysApi sysApi) throws BrightException {
        try {
            this.sysApiService.saveSysApi(sysApi);
        } catch (Exception e) {
            String message = "新增SysApi失败";
            log.error(message, e);
            throw new BrightException(message);
        }
    }

    @DeleteMapping("{ids}")
    @PreAuthorize("hasAuthority('sysApi:delete')")
    public void deleteSysApi(@NotBlank(message = "{required}") @PathVariable String ids) throws BrightException {
        try {
            String[] idArray = ids.split(StringConstant.COMMA);
            this.sysApiService.deleteSysApi(idArray);
        } catch (Exception e) {
            String message = "删除SysApi失败";
            log.error(message, e);
            throw new BrightException(message);
        }
    }

    @PutMapping
    @PreAuthorize("hasAuthority('sysApi:update')")
    public void updateSysApi(SysApi sysApi) throws BrightException {
        try {
            this.sysApiService.updateSysApi(sysApi);
        } catch (Exception e) {
            String message = "修改SysApi失败";
            log.error(message, e);
            throw new BrightException(message);
        }
    }
}
