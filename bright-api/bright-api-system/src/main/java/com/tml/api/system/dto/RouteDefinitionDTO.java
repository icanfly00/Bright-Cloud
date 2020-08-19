package com.tml.api.system.dto;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 路由模型
 * @Author TuMingLong
 * @Date 2020/7/15 14:58
 */
@Data
@Validated
public class RouteDefinitionDTO {
    /**
     * 路由Id
     */
    private String id;
    /**
     * 路由断言集合配置
     */
    @NotEmpty
    @Valid
    private List<PredicateDefinitionDTO> predicates = new ArrayList();
    /**
     * 路由过滤器集合配置
     */
    @Valid
    private List<FilterDefinitionDTO> filters = new ArrayList();
    /**
     * 路由规则转发的目标uri
     */
    @NotNull
    private URI uri;

    private Map<String, Object> metadata = new HashMap();
    /**
     * 路由执行的顺序
     */
    private int order = 0;
}
