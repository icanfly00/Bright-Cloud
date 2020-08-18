package com.tml.server.generator.controller;

import com.tml.common.core.entity.CommonResult;
import com.tml.server.generator.entity.GeneratorConfig;
import com.tml.common.core.exception.BrightException;
import com.tml.server.generator.service.IGeneratorConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @description
 * @author JacksonTu
 * @since 2020-08-10 20:30
 * @version 1.0
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("config")
public class GeneratorConfigController {

    private final IGeneratorConfigService generatorConfigService;

    @GetMapping
    @PreAuthorize("hasAuthority('gen:config')")
    public CommonResult getGeneratorConfig() {
        return new CommonResult().data(generatorConfigService.findGeneratorConfig());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('gen:config:update')")
    public void updateGeneratorConfig(@Valid GeneratorConfig generatorConfig) throws BrightException {
        if (StringUtils.isBlank(generatorConfig.getId())) {
            throw new BrightException("配置id不能为空");
        }
        this.generatorConfigService.updateGeneratorConfig(generatorConfig);
    }
}
