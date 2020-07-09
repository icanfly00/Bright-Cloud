package com.tml.common.exception;

import com.tml.common.api.CommonResult;
import com.tml.common.api.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

/**
 * @Description 全局异常处理
 * @Author TuMingLong
 * @Date 2020/3/28 16:41
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public CommonResult handleException(Exception e) {
        log.error("系统内部错误：{}",e);
       return CommonResult.failed();
    }

    @ExceptionHandler(value = APIException.class)
    public CommonResult handleAPIException(APIException e) {
        if(e.getCode()!=ResultCode.FAILED.getCode()){
            return CommonResult.failed(e.getCode(),e.getMessage());
        }else {
            return CommonResult.failed(e.getMessage());
        }
    }

    /**
     * 方法参数校验
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("handleMethodArgumentNotValidException: {}", e.getBindingResult().getFieldError().getDefaultMessage());
        return CommonResult.failed(e.getBindingResult().getFieldError().getDefaultMessage());
    }

    /**
     * ValidationException
     */
    @ExceptionHandler(ValidationException.class)
    public CommonResult handleValidationException(ValidationException e) {
        log.error("handleValidationException: {}", e.getCause().getMessage());
        return CommonResult.failed(e.getCause().getMessage());
    }

    /**
     * ConstraintViolationException
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public CommonResult handleConstraintViolationException(ConstraintViolationException e) {
        log.error("handleConstraintViolationException: {}", e.getMessage());
        return CommonResult.failed(e.getMessage());
    }

    /**
     * 静态解析异常。可以直接调用
     *
     * @param ex
     * @return
     */
    public static CommonResult resolveException(Exception ex, String path) {
        ResultCode code = ResultCode.FAILED;
        String message = ex.getMessage();
        String className = ex.getClass().getName();
        if (className.contains("UsernameNotFoundException")) {
           
            code = ResultCode.USERNAME_NOT_FOUND;
        } else if (className.contains("BadCredentialsException")) {
           
            code = ResultCode.BAD_CREDENTIALS;
        } else if (className.contains("AccountExpiredException")) {
           
            code = ResultCode.ACCOUNT_EXPIRED;
        } else if (className.contains("LockedException")) {
           
            code = ResultCode.ACCOUNT_LOCKED;
        } else if (className.contains("DisabledException")) {
           
            code = ResultCode.ACCOUNT_DISABLED;
        } else if (className.contains("CredentialsExpiredException")) {
           
            code = ResultCode.CREDENTIALS_EXPIRED;
        } else if (className.contains("InvalidClientException")) {
           
            code = ResultCode.INVALID_CLIENT;
        } else if (className.contains("UnauthorizedClientException")) {
           
            code = ResultCode.UNAUTHORIZED_CLIENT;
        } else if (className.contains("InsufficientAuthenticationException") || className.contains("AuthenticationCredentialsNotFoundException")) {
           
            code = ResultCode.UNAUTHORIZED;
        } else if (className.contains("InvalidGrantException")) {
            code = ResultCode.ALERT;
            if ("Bad credentials".contains(message)) {
                code = ResultCode.BAD_CREDENTIALS;
            } else if ("User is disabled".contains(message)) {
                code = ResultCode.ACCOUNT_DISABLED;
            } else if ("User account is locked".contains(message)) {
                code = ResultCode.ACCOUNT_LOCKED;
            }
        } else if (className.contains("InvalidScopeException")) {
           
            code = ResultCode.INVALID_SCOPE;
        } else if (className.contains("InvalidTokenException")) {
           
            code = ResultCode.INVALID_TOKEN;
        } else if (className.contains("InvalidRequestException")) {
            code = ResultCode.INVALID_REQUEST;
        } else if (className.contains("RedirectMismatchException")) {
            code = ResultCode.REDIRECT_URI_MISMATCH;
        } else if (className.contains("UnsupportedGrantTypeException")) {
            code = ResultCode.UNSUPPORTED_GRANT_TYPE;
        } else if (className.contains("UnsupportedResponseTypeException")) {
            code = ResultCode.UNSUPPORTED_RESPONSE_TYPE;
        } else if (className.contains("UserDeniedAuthorizationException")) {
            code = ResultCode.ACCESS_DENIED;
        } else if (className.contains("AccessDeniedException")) {
            code = ResultCode.ACCESS_DENIED;
            if (ResultCode.ACCESS_DENIED_BLACK_LIMITED.getMessage().contains(message)) {
                code = ResultCode.ACCESS_DENIED_BLACK_LIMITED;
            } else if (ResultCode.ACCESS_DENIED_WHITE_LIMITED.getMessage().contains(message)) {
                code = ResultCode.ACCESS_DENIED_WHITE_LIMITED;
            } else if (ResultCode.ACCESS_DENIED_AUTHORITY_EXPIRED.getMessage().contains(message)) {
                code = ResultCode.ACCESS_DENIED_AUTHORITY_EXPIRED;
            } else if (ResultCode.ACCESS_DENIED_UPDATING.getMessage().contains(message)) {
                code = ResultCode.ACCESS_DENIED_UPDATING;
            } else if (ResultCode.ACCESS_DENIED_DISABLED.getMessage().contains(message)) {
                code = ResultCode.ACCESS_DENIED_DISABLED;
            } else if (ResultCode.ACCESS_DENIED_NOT_OPEN.getMessage().contains(message)) {
                code = ResultCode.ACCESS_DENIED_NOT_OPEN;
            }
        } else if (className.contains("HttpMessageNotReadableException")
                || className.contains("TypeMismatchException")
                || className.contains("MissingServletRequestParameterException")) {
            code = ResultCode.BAD_REQUEST;
        } else if (className.contains("NoHandlerFoundException")) {
            code = ResultCode.NOT_FOUND;
        } else if (className.contains("HttpRequestMethodNotSupportedException")) {
            code = ResultCode.METHOD_NOT_ALLOWED;
        } else if (className.contains("HttpMediaTypeNotAcceptableException")) {
            code = ResultCode.MEDIA_TYPE_NOT_ACCEPTABLE;
        } else if (className.contains("MethodArgumentNotValidException")) {
            BindingResult bindingResult = ((MethodArgumentNotValidException) ex).getBindingResult();
            code = ResultCode.ALERT;
            return CommonResult.failed(bindingResult.getFieldError().getDefaultMessage());
        } else if (className.contains("IllegalArgumentException")) {
            //参数错误
            code = ResultCode.ALERT;
        } else if (className.contains("OpenAlertException")) {
            code = ResultCode.ALERT;
        } else if (className.contains("OpenSignatureException")) {
            code = ResultCode.SIGNATURE_DENIED;
        } else if (message.equalsIgnoreCase(ResultCode.TOO_MANY_REQUESTS.name())) {
            code = ResultCode.TOO_MANY_REQUESTS;
        }
        return CommonResult.failed(code);
    }
}
