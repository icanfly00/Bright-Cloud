package com.tml.server.msg.controller;

import com.tml.server.msg.entity.SysNoticeSend;
import com.tml.server.msg.service.ISysNoticeSendService;
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
 * 用户通告阅读标记表 Controller
 *
 * @author JacksonTu
 * @date 2020-10-12 15:03:04
 */
@Slf4j
@Validated
@RestController
@RequestMapping("notice/send")
@RequiredArgsConstructor
public class SysNoticeSendController {

    private final ISysNoticeSendService sysNoticeSendService;

    @GetMapping
    @PreAuthorize("hasAuthority('notice:send:list')")
    public CommonResult listSysNoticeSend(SysNoticeSend sysNoticeSend) {
        return new CommonResult().data(sysNoticeSendService.listSysNoticeSend(sysNoticeSend));
    }

    @GetMapping("list")
    @PreAuthorize("hasAuthority('notice:send:list')")
    public CommonResult pageSysNoticeSend(QueryRequest request, SysNoticeSend sysNoticeSend) {
        Map<String, Object> dataTable = BrightUtil.getDataTable(this.sysNoticeSendService.pageSysNoticeSend(request, sysNoticeSend));
        return new CommonResult().data(dataTable);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('notice:send:add')")
    public void saveSysNoticeSend(@Valid SysNoticeSend sysNoticeSend) throws BrightException {
        try {
            this.sysNoticeSendService.saveSysNoticeSend(sysNoticeSend);
        } catch (Exception e) {
            String message = "新增SysNoticeSend失败";
            log.error(message, e);
            throw new BrightException(message);
        }
    }

    @DeleteMapping("{ids}")
    @PreAuthorize("hasAuthority('notice:send:delete')")
    public void deleteSysNoticeSend(@NotBlank(message = "{required}") @PathVariable String ids) throws BrightException {
        try {
            String[] idArray = ids.split(StringConstant.COMMA);
            this.sysNoticeSendService.deleteSysNoticeSend(idArray);
        } catch (Exception e) {
            String message = "删除SysNoticeSend失败";
            log.error(message, e);
            throw new BrightException(message);
        }
    }

    @PutMapping
    @PreAuthorize("hasAuthority('notice:send:update')")
    public void updateSysNoticeSend(SysNoticeSend sysNoticeSend) throws BrightException {
        try {
            this.sysNoticeSendService.updateSysNoticeSend(sysNoticeSend);
        } catch (Exception e) {
            String message = "修改SysNoticeSend失败";
            log.error(message, e);
            throw new BrightException(message);
        }
    }
}
