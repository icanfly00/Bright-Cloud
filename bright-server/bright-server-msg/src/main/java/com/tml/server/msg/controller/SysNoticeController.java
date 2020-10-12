package com.tml.server.msg.controller;

import com.tml.server.msg.entity.SysNotice;
import com.tml.server.msg.service.ISysNoticeService;
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
 * 系统通告表 Controller
 *
 * @author JacksonTu
 * @date 2020-10-12 15:02:54
 */
@Slf4j
@Validated
@RestController
@RequestMapping("notice")
@RequiredArgsConstructor
public class SysNoticeController {

    private final ISysNoticeService sysNoticeService;

    @GetMapping
    @PreAuthorize("hasAuthority('notice:list')")
    public CommonResult listSysNotice(SysNotice sysNotice) {
        return new CommonResult().data(sysNoticeService.listSysNotice(sysNotice));
    }

    @GetMapping("list")
    @PreAuthorize("hasAuthority('notice:list')")
    public CommonResult pageSysNotice(QueryRequest request, SysNotice sysNotice) {
        Map<String, Object> dataTable = BrightUtil.getDataTable(this.sysNoticeService.pageSysNotice(request, sysNotice));
        return new CommonResult().data(dataTable);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('notice:add')")
    public void saveSysNotice(@Valid SysNotice sysNotice) throws BrightException {
        try {
            this.sysNoticeService.saveSysNotice(sysNotice);
        } catch (Exception e) {
            String message = "新增SysNotice失败";
            log.error(message, e);
            throw new BrightException(message);
        }
    }

    @DeleteMapping("{ids}")
    @PreAuthorize("hasAuthority('notice:delete')")
    public void deleteSysNotice(@NotBlank(message = "{required}") @PathVariable String ids) throws BrightException {
        try {
            String[] idArray = ids.split(StringConstant.COMMA);
            this.sysNoticeService.deleteSysNotice(idArray);
        } catch (Exception e) {
            String message = "删除SysNotice失败";
            log.error(message, e);
            throw new BrightException(message);
        }
    }

    @PutMapping
    @PreAuthorize("hasAuthority('notice:update')")
    public void updateSysNotice(SysNotice sysNotice) throws BrightException {
        try {
            this.sysNoticeService.updateSysNotice(sysNotice);
        } catch (Exception e) {
            String message = "修改SysNotice失败";
            log.error(message, e);
            throw new BrightException(message);
        }
    }
}
