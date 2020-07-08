package com.tml.common.datasource.tenant;

import com.tml.common.constant.CommonConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description 多租户上下文过滤器 -设置加载顺序最高获取租户
 * @Author TuMingLong
 * @Date 2020/3/31 15:48
 */
@Slf4j
@Component
@Order(1)
public class RestTenantContextHolderFilter extends OncePerRequestFilter {

    public RestTenantContextHolderFilter() {
        log.info("-----RestTenantContextHolderFilter init-----");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String tenantId = request.getHeader(CommonConstant.TENANT_KEY);
        log.info("前台传递过来的租户ID：{}", tenantId);
        //在没有提供tenantId的情况下返回默认的
        if (!StringUtils.isEmpty(tenantId)) {
            RestTenantContextHolder.setCurrentTenantId(Long.valueOf(tenantId));
        } else {
            RestTenantContextHolder.setCurrentTenantId(1L);
        }
        filterChain.doFilter(request, response);
    }


}
