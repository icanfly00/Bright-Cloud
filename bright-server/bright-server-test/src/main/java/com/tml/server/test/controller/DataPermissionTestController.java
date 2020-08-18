package com.tml.server.test.controller;

import com.tml.common.core.entity.CommonResult;
import com.tml.common.core.entity.QueryRequest;
import com.tml.common.core.utils.BrightUtil;
import com.tml.server.test.entity.DataPermissionTest;
import com.tml.server.test.service.IDataPermissionTestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Controller
 *
 * @Author TuMingLong
 * @date 2020-04-14 15:25:33
 */
@Slf4j
@RestController
@RequestMapping("dataPermissionTest")
@RequiredArgsConstructor
public class DataPermissionTestController {

    private final IDataPermissionTestService dataPermissionTestService;

    @GetMapping("list")
    @PreAuthorize("hasAuthority('others:datapermission')")
    public CommonResult dataPermissionTestList(QueryRequest request, DataPermissionTest dataPermissionTest) {
        Map<String, Object> dataTable = BrightUtil.getDataTable(this.dataPermissionTestService.findDataPermissionTests(request, dataPermissionTest));
        return new CommonResult().data(dataTable);
    }
}
