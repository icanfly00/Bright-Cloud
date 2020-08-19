package com.tml.server.system.controller;

import com.tml.api.system.entity.SysDept;
import com.tml.common.core.entity.CommonResult;
import com.tml.common.core.entity.QueryRequest;
import com.tml.common.core.entity.constant.StringConstant;
import com.tml.server.system.annotation.ControllerEndpoint;
import com.tml.server.system.service.ISysDeptService;
import com.wuwenze.poi.ExcelKit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
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
@Validated
@RestController
@RequestMapping("dept")
@RequiredArgsConstructor
public class SysDeptController {

    private final ISysDeptService deptService;

    @GetMapping
    public CommonResult deptList(QueryRequest request, SysDept dept) {
        Map<String, Object> depts = this.deptService.findDepts(request, dept);
        return new CommonResult().data(depts);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('dept:add')")
    @ControllerEndpoint(operation = "新增部门", exceptionMessage = "新增部门失败")
    public void addDept(@Valid SysDept dept) {
        this.deptService.createDept(dept);
    }

    @DeleteMapping("/{deptIds}")
    @PreAuthorize("hasAuthority('dept:delete')")
    @ControllerEndpoint(operation = "删除部门", exceptionMessage = "删除部门失败")
    public void deleteDepts(@NotBlank(message = "{required}") @PathVariable String deptIds) {
        String[] ids = deptIds.split(StringConstant.COMMA);
        this.deptService.deleteDepts(ids);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('dept:update')")
    @ControllerEndpoint(operation = "修改部门", exceptionMessage = "修改部门失败")
    public void updateDept(@Valid SysDept dept) {
        this.deptService.updateDept(dept);
    }


    @PostMapping("excel")
    @PreAuthorize("hasAuthority('role:export')")
    @ControllerEndpoint(operation = "导出部门数据", exceptionMessage = "导出部门数据")
    public void export(QueryRequest request, SysDept dept, HttpServletResponse response) {
        List<SysDept> list = this.deptService.findDepts(dept, request);
        ExcelKit.$Export(SysDept.class, response).downXlsx(list, false);
    }
}
