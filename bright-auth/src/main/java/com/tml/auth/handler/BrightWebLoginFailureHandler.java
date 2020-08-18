package com.tml.auth.handler;

import com.tml.common.core.entity.CommonResult;
import com.tml.common.core.utils.BrightUtil;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description
 * @author JacksonTu
 * @since 2020-08-10 20:30
 * @version 1.0
 */
@Component
public class BrightWebLoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException exception) throws IOException {
        String message;
        if (exception instanceof BadCredentialsException) {
            message = "用户名或密码错误！";
        } else if (exception instanceof LockedException) {
            message = "用户已被锁定！";
        } else {
            message = "认证失败，请联系网站管理员！";
        }
        CommonResult commonResult = new CommonResult().message(message);
        BrightUtil.makeFailureResponse(httpServletResponse, commonResult);
    }
}
