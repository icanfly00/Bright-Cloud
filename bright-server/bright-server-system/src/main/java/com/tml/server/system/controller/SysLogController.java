package com.tml.server.system.controller;

import com.tml.api.system.entity.SysLog;
import com.tml.common.core.entity.CommonResult;
import com.tml.common.core.entity.QueryRequest;
import com.tml.common.core.entity.constant.StringConstant;
import com.tml.common.core.utils.BrightUtil;
import com.tml.server.system.annotation.ControllerEndpoint;
import com.tml.server.system.service.ISysLogService;
import com.wuwenze.poi.ExcelKit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
 * @author JacksonTu
 * @version 1.0
 * @description
 * @since 2020-08-10 20:30
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("log")
public class SysLogController {

    private final ISysLogService logService;

    @GetMapping
    public CommonResult logList(SysLog log, QueryRequest request) {
        Map<String, Object> dataTable = BrightUtil.getDataTable(this.logService.pageLog(log, request));
        return new CommonResult().data(dataTable);
    }

    @DeleteMapping("{ids}")
    @PreAuthorize("hasAuthority('log:delete')")
    @ControllerEndpoint(exceptionMessage = "删除日志失败")
    public void deleteLogs(@NotBlank(message = "{required}") @PathVariable String ids) {
        String[] logIds = ids.split(StringConstant.COMMA);
        this.logService.deleteLogs(logIds);
    }


    @PostMapping("excel")
    @PreAuthorize("hasAuthority('log:export')")
    public void export(QueryRequest request, SysLog log, HttpServletResponse response) {
        List<SysLog> list = this.logService.pageLog(log, request).getRecords();
        ExcelKit.$Export(SysLog.class, response).downXlsx(list, false);
    }
}
