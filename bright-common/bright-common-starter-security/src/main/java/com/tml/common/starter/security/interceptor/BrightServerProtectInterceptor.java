package com.tml.common.starter.security.interceptor;

import com.tml.common.core.entity.CommonResult;
import com.tml.common.core.entity.constant.BrightConstant;
import com.tml.common.core.utils.BrightUtil;
import com.tml.common.starter.security.properties.BrightCloudSecurityProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.util.Base64Utils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description 
 * @author JacksonTu
 * @since 2020-08-10 20:30
 * @version 1.0
 */
public class BrightServerProtectInterceptor implements HandlerInterceptor {

    private BrightCloudSecurityProperties properties;

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws IOException {
        if (!properties.getOnlyFetchByGateway()) {
            return true;
        }
        String token = request.getHeader(BrightConstant.GATEWAY_TOKEN_HEADER);
        String gatewayToken = new String(Base64Utils.encode(BrightConstant.GATEWAY_TOKEN_VALUE.getBytes()));
        if (StringUtils.equals(gatewayToken, token)) {
            return true;
        } else {
            CommonResult commonResult = new CommonResult();
            BrightUtil.makeJsonResponse(response, HttpServletResponse.SC_FORBIDDEN, commonResult.message("请通过网关获取资源"));
            return false;
        }
    }

    public void setProperties(BrightCloudSecurityProperties properties) {
        this.properties = properties;
    }
}
