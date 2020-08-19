package com.tml.api.system.dto;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author TuMingLong
 * @description 断言模型
 * @sence 2020/7/15 14:59
 */
@Data
@Validated
public class PredicateDefinitionDTO {
    /**
     * 断言名称
     */
    @NotNull
    private String name;
    /**
     * 配置的断言规则
     */
    private Map<String, String> args = new LinkedHashMap<>();

}
