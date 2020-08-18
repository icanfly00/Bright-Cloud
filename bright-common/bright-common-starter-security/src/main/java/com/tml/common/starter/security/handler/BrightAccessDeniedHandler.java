package com.tml.common.starter.security.handler;

import com.tml.common.core.entity.CommonResult;
import com.tml.common.core.utils.BrightUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description
 * @author JacksonTu
 * @since 2020-08-10 20:30
 * @version 1.0
 */
public class BrightAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        CommonResult commonResult = new CommonResult();
        BrightUtil.makeJsonResponse(response, HttpServletResponse.SC_FORBIDDEN, commonResult.message("没有权限访问该资源"));
    }
}
