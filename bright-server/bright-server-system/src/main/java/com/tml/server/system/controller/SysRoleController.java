package com.tml.server.system.controller;

import com.tml.api.system.entity.SysRole;
import com.tml.common.core.entity.CommonResult;
import com.tml.common.core.entity.QueryRequest;
import com.tml.common.core.entity.constant.StringConstant;
import com.tml.common.core.utils.BrightUtil;
import com.tml.server.system.annotation.ControllerEndpoint;
import com.tml.server.system.service.ISysRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
 * @description
 * @author JacksonTu
 * @since 2020-08-10 20:30
 * @version 1.0
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("role")
public class SysRoleController {

    private final ISysRoleService roleService;

    @GetMapping
    public CommonResult roleList(QueryRequest queryRequest, SysRole role) {
        Map<String, Object> dataTable = BrightUtil.getDataTable(roleService.findRoles(role, queryRequest));
        return new CommonResult().data(dataTable);
    }

    @GetMapping("options")
    public CommonResult roles() {
        List<SysRole> allRoles = roleService.findAllRoles();
        return new CommonResult().data(allRoles);
    }

    @GetMapping("check/{roleName}")
    public boolean checkRoleName(@NotBlank(message = "{required}") @PathVariable String roleName) {
        SysRole result = this.roleService.findByName(roleName);
        return result == null;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('role:add')")
    @ControllerEndpoint(operation = "新增角色", exceptionMessage = "新增角色失败")
    public void addRole(@Valid SysRole role) {
        this.roleService.createRole(role);
    }

    @DeleteMapping("/{roleIds}")
    @PreAuthorize("hasAuthority('role:delete')")
    @ControllerEndpoint(operation = "删除角色", exceptionMessage = "删除角色失败")
    public void deleteRoles(@NotBlank(message = "{required}") @PathVariable String roleIds) {
        String[] ids = roleIds.split(StringConstant.COMMA);
        this.roleService.deleteRoles(ids);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('role:update')")
    @ControllerEndpoint(operation = "修改角色", exceptionMessage = "修改角色失败")
    public void updateRole(@Valid SysRole role) {
        this.roleService.updateRole(role);
    }

}
