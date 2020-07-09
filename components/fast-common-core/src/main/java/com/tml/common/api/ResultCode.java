package com.tml.common.api;

/**
 * @Description 枚举一些常用API操作码
 * @Author TuMingLong
 * @Date 2020/3/28 16:26
 */
public enum ResultCode implements IErrorCode {

    SUCCESS(200, "操作成功"),
    FAILED(500, "系统内部异常"),
    VALIDATE_FAILED(404, "参数检验失败"),
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    FORBIDDEN(403, "没有权限访问该资源"),
    ALERT(406, "alert"),


    /**
     * oauth2返回码
     */
    INVALID_TOKEN(2000, "invalid_token"),
    INVALID_SCOPE(2001, "invalid_scope"),
    INVALID_REQUEST(2002, "invalid_request"),
    INVALID_CLIENT(2003, "invalid_client"),
    INVALID_GRANT(2004, "invalid_grant"),
    REDIRECT_URI_MISMATCH(2005, "redirect_uri_mismatch"),
    UNAUTHORIZED_CLIENT(2006, "unauthorized_client"),
    EXPIRED_TOKEN(2007, "expired_token"),
    UNSUPPORTED_GRANT_TYPE(2008, "unsupported_grant_type"),
    UNSUPPORTED_RESPONSE_TYPE(2009, "unsupported_response_type"),
    SIGNATURE_DENIED(2010, "signature_denied"),

    ACCESS_DENIED(2011, "access_denied"),
    ACCESS_DENIED_BLACK_LIMITED(2012, "access_denied_black_limited"),
    ACCESS_DENIED_WHITE_LIMITED(2013, "access_denied_white_limited"),
    ACCESS_DENIED_AUTHORITY_EXPIRED(2014, "access_denied_authority_expired"),
    ACCESS_DENIED_UPDATING(2015, "access_denied_updating"),
    ACCESS_DENIED_DISABLED(2016, "access_denied_disabled"),
    ACCESS_DENIED_NOT_OPEN(2017, "access_denied_not_open"),
    /**
     * 账号错误
     */
    BAD_CREDENTIALS(3000, "bad_credentials"),
    ACCOUNT_DISABLED(3001, "account_disabled"),
    ACCOUNT_EXPIRED(3002, "account_expired"),
    CREDENTIALS_EXPIRED(3003, "credentials_expired"),
    ACCOUNT_LOCKED(3004, "account_locked"),
    USERNAME_NOT_FOUND(3005, "username_not_found"),

    /**
     * 请求错误
     */
    BAD_REQUEST(4000, "bad_request"),
    NOT_FOUND(4001, "not_found"),
    METHOD_NOT_ALLOWED(4002, "method_not_allowed"),
    MEDIA_TYPE_NOT_ACCEPTABLE(4003, "media_type_not_acceptable"),
    TOO_MANY_REQUESTS(4004, "too_many_requests"),
    /**
     * 系统错误
     */
    GATEWAY_TIMEOUT(5000, "gateway_timeout"),
    SERVICE_UNAVAILABLE(5001, "service_unavailable");


    private Integer code;
    private String message;

    private ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }


}
