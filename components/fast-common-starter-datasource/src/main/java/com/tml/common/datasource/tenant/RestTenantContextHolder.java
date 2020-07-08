package com.tml.common.datasource.tenant;

import com.google.common.collect.Maps;
import lombok.experimental.UtilityClass;

import java.util.Map;

/**
 * @Description 多租户上下文
 * @Author TuMingLong
 * @Date 2020/3/31 15:44
 */
@UtilityClass
public class RestTenantContextHolder {

    private static final String KEY_CURRENT_TENANT_ID = "KEY_CURRENT_TENANT_ID";
    private static final Map<String, Object> PRE_TENANT_CONTEXT = Maps.newConcurrentMap();

    /**
     * 设置租户Id
     *
     * @param providerId
     */
    public void setCurrentTenantId(Long providerId) {
        PRE_TENANT_CONTEXT.put(KEY_CURRENT_TENANT_ID, providerId);
    }

    /**
     * 获取租户Id
     *
     * @return
     */
    public Long getCurrentTenantId() {
        return (Long) PRE_TENANT_CONTEXT.get(KEY_CURRENT_TENANT_ID);
    }

    /**
     * 清除租户信息
     */
    public void clear() {
        PRE_TENANT_CONTEXT.clear();
    }
}
