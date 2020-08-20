package com.tml.server.test.controller;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.tml.common.core.entity.constant.StringConstant;
import com.tml.server.test.entity.OmsItem;
import com.tml.server.test.service.IOmsItemService;
import com.tml.common.core.entity.CommonResult;
import com.tml.common.core.entity.QueryRequest;
import com.tml.common.core.exception.BrightException;
import com.tml.common.core.utils.BrightUtil;
import com.wuwenze.poi.ExcelKit;
import com.wuwenze.poi.handler.ExcelReadHandler;
import com.wuwenze.poi.pojo.ExcelErrorField;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

/**
 * 商品表 Controller
 *
 * @author JacksonTu
 * @date 2020-08-20 11:49:45
 */
@Slf4j
@Validated
@RestController
@RequestMapping("omsItem")
@RequiredArgsConstructor
public class OmsItemController {

    private static final String XLSX = ".xlsx";

    private final IOmsItemService omsItemService;

    @GetMapping("check")
    public CommonResult check(@RequestParam("itemName") String itemName,@RequestParam("itemCode") String itemCode) {
        return new CommonResult().data(omsItemService.check(itemName,itemCode));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('omsItem:list')")
    public CommonResult listOmsItem(OmsItem omsItem) {
        return new CommonResult().data(omsItemService.listOmsItem(omsItem));
    }

    @GetMapping("list")
    @PreAuthorize("hasAuthority('omsItem:list')")
    public CommonResult pageOmsItem(QueryRequest request, OmsItem omsItem) {
        Map<String, Object> dataTable = BrightUtil.getDataTable(this.omsItemService.pageOmsItem(request, omsItem));
        return new CommonResult().data(dataTable);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('omsItem:add')")
    public void saveOmsItem(@Valid OmsItem omsItem) throws BrightException {
        try {
            omsItem.setCreateTime(new Date());
            omsItem.setCreateUserId(BrightUtil.getCurrentUser().getUserId());
            omsItem.setDeptId(BrightUtil.getCurrentUser().getDeptId());
            Snowflake snowflake = IdUtil.getSnowflake(1, 1);
            long id = snowflake.nextId();
            omsItem.setItemCode(new Long(id).toString());
            this.omsItemService.saveOmsItem(omsItem);
        } catch (Exception e) {
            String message = "新增OmsItem失败";
            log.error(message, e);
            throw new BrightException(message);
        }
    }

    @DeleteMapping("{ids}")
    @PreAuthorize("hasAuthority('omsItem:delete')")
    public void deleteOmsItem(@NotBlank(message = "{required}") @PathVariable String ids) throws BrightException {
        try {
            String[] idArray = ids.split(StringConstant.COMMA);
            this.omsItemService.deleteOmsItem(idArray);
        } catch (Exception e) {
            String message = "删除OmsItem失败";
            log.error(message, e);
            throw new BrightException(message);
        }
    }

    @PutMapping
    @PreAuthorize("hasAuthority('omsItem:update')")
    public void updateOmsItem(OmsItem omsItem) throws BrightException {
        try {
            omsItem.setUpdateTime(new Date());
            omsItem.setUpdateUserId(BrightUtil.getCurrentUser().getUserId());
            omsItem.setDeptId(BrightUtil.getCurrentUser().getDeptId());
            this.omsItemService.updateOmsItem(omsItem);
        } catch (Exception e) {
            String message = "修改OmsItem失败";
            log.error(message, e);
            throw new BrightException(message);
        }
    }

    /**
     * 导出Excel
     * @param queryRequest
     * @param omsItem
     * @param response
     */
    @PostMapping("excel")
    @PreAuthorize("hasAuthority('omsItem:export')")
    public void export(QueryRequest queryRequest, OmsItem omsItem, HttpServletResponse response) {
        List<OmsItem> list = this.omsItemService.pageOmsItem(queryRequest, omsItem).getRecords();
        ExcelKit.$Export(OmsItem.class, response).downXlsx(list, false);
    }

    /**
     * 下载模板
     * @param response
     */
    @PostMapping("template")
    @PreAuthorize("hasAuthority('omsItem:template')")
    public void downloadTemplate(HttpServletResponse response) {
        List<OmsItem> list = new ArrayList<>();
        IntStream.range(0, 1000).forEach(i -> {
            OmsItem omsItem = new OmsItem();
            omsItem.setItemName("商品"+i);
            Snowflake snowflake = IdUtil.getSnowflake(1, 1);
            long id = snowflake.nextId();
            omsItem.setItemCode(new Long(id).toString());
            omsItem.setItemStock(100000);
            omsItem.setStatus("1");
            list.add(omsItem);
        });
        ExcelKit.$Export(OmsItem.class, response).downXlsx(list, true);
    }

    /**
     * 导入Excel
     * @param file
     * @return
     * @throws IOException
     * @throws BrightException
     */
    @PostMapping("import")
    @PreAuthorize("hasAuthority('omsItem:import')")
    public CommonResult importExcel(MultipartFile file) throws IOException, BrightException {
        if (file.isEmpty()) {
            throw new BrightException("导入数据为空");
        }
        String filename = file.getOriginalFilename();
        if (!StringUtils.endsWith(filename, XLSX)) {
            throw new BrightException("只支持.xlsx类型文件导入");
        }
        Stopwatch stopwatch = Stopwatch.createStarted();
        final List<OmsItem> data = Lists.newArrayList();
        final List<Map<String, Object>> error = Lists.newArrayList();
        ExcelKit.$Import(OmsItem.class).readXlsx(file.getInputStream(), new ExcelReadHandler<OmsItem>() {
            @Override
            public void onSuccess(int sheet, int row, OmsItem omsItem) {
                omsItem.setCreateTime(new Date());
                omsItem.setCreateUserId(BrightUtil.getCurrentUser().getUserId());
                omsItem.setDeptId(BrightUtil.getCurrentUser().getDeptId());
                data.add(omsItem);
            }

            @Override
            public void onError(int sheet, int row, List<ExcelErrorField> errorFields) {
                error.add(ImmutableMap.of("row", row, "errorFields", errorFields));
            }
        });
        if (CollectionUtils.isNotEmpty(data)) {
            this.omsItemService.saveBatch(data);
        }
        ImmutableMap<String, Object> result = ImmutableMap.of(
                "time", stopwatch.stop().toString(),
                "data", data,
                "error", error
        );
        return new CommonResult().data(result);
    }
}
