package com.tml.common.security.handler;

import com.tml.common.api.CommonResult;
import com.tml.common.exception.GlobalExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 自定义oauth2异常提示
 *
 * @author liuyadu
 */
@Slf4j
public class RestOAuth2WebResponseExceptionTranslator implements WebResponseExceptionTranslator {

    @Override
    public ResponseEntity translate(Exception e) throws Exception {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        CommonResult responseData = GlobalExceptionHandler.resolveException(e, request.getRequestURI());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(responseData);
    }
}
