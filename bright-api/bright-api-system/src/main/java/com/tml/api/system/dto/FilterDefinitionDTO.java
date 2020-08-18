package com.tml.api.system.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Description 过滤器模型
 * @Author TuMingLong
 * @Date 2020/7/15 14:59
 */
@Data
@Validated
public class FilterDefinitionDTO {
    /**
     * 过滤器名称
     */
    @NotNull
    private String name;
    /**
     * 配置的路由规则
     */
    private Map<String, String> args = new LinkedHashMap<>();
}
