package com.tml.common.datasource.tenant;

import com.baomidou.mybatisplus.extension.plugins.tenant.TenantHandler;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.NullValue;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Description 租户处理器
 * 实现mybatis-plus https://mp.baomidou.com/guide/tenant.html
 * @Author TuMingLong
 * @Date 2020/3/31 15:56
 */
@Slf4j
@Component
public class RestTenantHandler implements TenantHandler {

    @Resource
    private RestTenantConfigProperties restTenantConfigProperties;

    /**
     * 租户Id
     *
     * @param where
     * @return
     */
    @Override
    public Expression getTenantId(boolean where) {
        Long tenantId = RestTenantContextHolder.getCurrentTenantId();
        log.info("当前租户为：{}", tenantId);
        if (tenantId == null) {
            return new NullValue();
        }
        return new LongValue(tenantId);
    }

    /**
     * 租户字段名
     *
     * @return
     */
    @Override
    public String getTenantIdColumn() {
        return restTenantConfigProperties.getTenantIdColumn();
    }

    /**
     * 根据表名判断是否进行过滤
     * 忽略掉一些表：如租户表（sys_tenant）本身不需要执行这样的处理
     *
     * @param tableName
     * @return
     */
    @Override
    public boolean doTableFilter(String tableName) {
        return restTenantConfigProperties.getIgnoreTenantTables()
                .stream().anyMatch(
                        (e) -> e.equalsIgnoreCase(tableName)
                );
    }
}
