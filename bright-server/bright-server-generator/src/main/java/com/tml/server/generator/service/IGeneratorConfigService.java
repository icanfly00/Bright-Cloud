package com.tml.server.generator.service;

import com.tml.server.generator.entity.GeneratorConfig;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @description 
 * @author JacksonTu
 * @since 2020-08-10 20:30
 * @version 1.0
 */
public interface IGeneratorConfigService extends IService<GeneratorConfig> {

    /**
     * 查询
     *
     * @return GeneratorConfig
     */
    GeneratorConfig findGeneratorConfig();

    /**
     * 修改
     *
     * @param generatorConfig generatorConfig
     */
    void updateGeneratorConfig(GeneratorConfig generatorConfig);

}
