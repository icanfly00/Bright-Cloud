package com.tml.common.datasource.tenant;

import com.google.common.collect.Lists;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description 多租户动态配置
 * @Author TuMingLong
 * @Date 2020/3/31 15:41
 */
@Data
@Component
@ConfigurationProperties(prefix = "tml.tenant")
public class RestTenantConfigProperties {

    /**
     * 维护租户ID
     */
    private String tenantIdColumn;

    /**
     * 多租户中需要忽略数据表集合
     */
    private List<String> ignoreTenantTables = Lists.newCopyOnWriteArrayList();
}
