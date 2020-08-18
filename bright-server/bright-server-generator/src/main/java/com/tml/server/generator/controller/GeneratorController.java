package com.tml.server.generator.controller;

import com.tml.common.core.entity.CommonResult;
import com.tml.common.core.entity.QueryRequest;
import com.tml.common.core.entity.constant.GeneratorConstant;
import com.tml.server.generator.entity.Column;
import com.tml.server.generator.entity.GeneratorConfig;
import com.tml.common.core.exception.BrightException;
import com.tml.common.core.utils.BrightUtil;
import com.tml.common.core.utils.FileUtil;
import com.tml.server.generator.helper.GeneratorHelper;
import com.tml.server.generator.service.IGeneratorConfigService;
import com.tml.server.generator.service.IGeneratorService;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
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
public class GeneratorController {

    private static final String SUFFIX = "_code.zip";

    private final IGeneratorService generatorService;
    private final IGeneratorConfigService generatorConfigService;
    private final GeneratorHelper generatorHelper;
    private final DynamicDataSourceProperties properties;

    @GetMapping("datasources")
    @PreAuthorize("hasAuthority('gen:generate')")
    public CommonResult datasources() {
        Map<String, DataSourceProperty> datasources = properties.getDatasource();
        List<String> datasourcesName = new ArrayList<>();
        datasources.forEach((k, v) -> {
            String datasourceName = StringUtils.substringBefore(StringUtils.substringAfterLast(v.getUrl(), "/"), "?");
            datasourcesName.add(datasourceName);
        });
        return new CommonResult().data(datasourcesName);
    }

    @GetMapping("tables")
    @PreAuthorize("hasAuthority('gen:generate')")
    public CommonResult tablesInfo(String tableName, String datasource, QueryRequest request) {
        Map<String, Object> dataTable = BrightUtil.getDataTable(generatorService.getTables(tableName, request, GeneratorConstant.DATABASE_TYPE, datasource));
        return new CommonResult().data(dataTable);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('gen:generate:gen')")
    public void generate(@NotBlank(message = "{required}") String name,
                         @NotBlank(message = "{required}") String datasource,
                         String remark, HttpServletResponse response) throws Exception {
        GeneratorConfig generatorConfig = generatorConfigService.findGeneratorConfig();
        if (generatorConfig == null) {
            throw new BrightException("代码生成配置为空");
        }

        String className = name;
        if (GeneratorConfig.TRIM_YES.equals(generatorConfig.getIsTrim())) {
            className = RegExUtils.replaceFirst(name, generatorConfig.getTrimValue(), StringUtils.EMPTY);
        }

        generatorConfig.setTableName(name);
        generatorConfig.setClassName(BrightUtil.underscoreToCamel(className));
        generatorConfig.setTableComment(remark);
        // 生成代码到临时目录
        List<Column> columns = generatorService.getColumns(GeneratorConstant.DATABASE_TYPE, datasource, name);
        generatorHelper.generateEntityFile(columns, generatorConfig);
        generatorHelper.generateMapperFile(columns, generatorConfig);
        generatorHelper.generateMapperXmlFile(columns, generatorConfig);
        generatorHelper.generateServiceFile(columns, generatorConfig);
        generatorHelper.generateServiceImplFile(columns, generatorConfig);
        generatorHelper.generateControllerFile(columns, generatorConfig);
        // 打包
        String zipFile = System.currentTimeMillis() + SUFFIX;
        FileUtil.compress(GeneratorConstant.TEMP_PATH + "src", zipFile);
        // 下载
        FileUtil.download(zipFile, name + SUFFIX, true, response);
        // 删除临时目录
        FileUtil.delete(GeneratorConstant.TEMP_PATH);
    }
}
