package com.tml.server.system.controller;

import com.tml.api.system.entity.SysMenu;
import com.tml.common.core.entity.CommonResult;
import com.tml.common.core.entity.constant.StringConstant;
import com.tml.common.core.entity.router.VueRouter;
import com.tml.server.system.annotation.ControllerEndpoint;
import com.tml.server.system.service.ISysMenuService;
import com.wuwenze.poi.ExcelKit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;
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
@RequiredArgsConstructor
@RequestMapping("/menu")
public class SysMenuController {

    private final ISysMenuService menuService;

    @GetMapping("/{username}")
    public CommonResult getUserRouters(@NotBlank(message = "{required}") @PathVariable String username) {
        Map<String, Object> result = new HashMap<>(2);
        List<VueRouter<SysMenu>> userRouters = this.menuService.getUserRouters(username);
        String userPermissions = this.menuService.findUserPermissions(username);
        String[] permissionArray = new String[0];
        if (StringUtils.isNoneBlank(userPermissions)) {
            permissionArray = StringUtils.splitByWholeSeparatorPreserveAllTokens(userPermissions, StringConstant.COMMA);
        }
        result.put("routes", userRouters);
        result.put("permissions", permissionArray);
        return new CommonResult().data(result);
    }

    @GetMapping
    public CommonResult menuList(SysMenu menu) {
        Map<String, Object> menus = this.menuService.findMenus(menu);
        return new CommonResult().data(menus);
    }

    @GetMapping("/permissions")
    public String findUserPermissions(String username) {
        return this.menuService.findUserPermissions(username);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('menu:add')")
    @ControllerEndpoint(operation = "新增菜单/按钮", exceptionMessage = "新增菜单/按钮失败")
    public void addMenu(@Valid SysMenu menu) {
        this.menuService.createMenu(menu);
    }

    @DeleteMapping("/{menuIds}")
    @PreAuthorize("hasAuthority('menu:delete')")
    @ControllerEndpoint(operation = "删除菜单/按钮", exceptionMessage = "删除菜单/按钮失败")
    public void deleteMenus(@NotBlank(message = "{required}") @PathVariable String menuIds) {
        String[] ids = menuIds.split(StringConstant.COMMA);
        this.menuService.deleteMenus(ids);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('menu:update')")
    @ControllerEndpoint(operation = "修改菜单/按钮", exceptionMessage = "修改菜单/按钮失败")
    public void updateMenu(@Valid SysMenu menu) {
        this.menuService.updateMenu(menu);
    }


    @PostMapping("excel")
    @PreAuthorize("hasAuthority('menu:export')")
    @ControllerEndpoint(operation = "导出菜单数据", exceptionMessage = "导出菜单数据")
    public void export(SysMenu menu, HttpServletResponse response) {
        List<SysMenu> list = this.menuService.findMenuList(menu);
        ExcelKit.$Export(SysMenu.class, response).downXlsx(list, false);
    }

}