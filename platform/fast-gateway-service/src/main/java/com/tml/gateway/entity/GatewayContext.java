package com.tml.gateway.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * @Description com.tml.gateway.entity
 * @Author TuMingLong
 * @Date 2020/8/2
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class GatewayContext {

    public static final String CACHE_GATEWAY_CONTEXT = "cacheGatewayContext";
    /**
     * cache json body
     */
    private String requestBody;
    /**
     * cache Response Body
     */
    private Object responseBody;
    /**
     * request headers
     */
    private HttpHeaders requestHeaders;
    /**
     * cache form data
     */
    private MultiValueMap<String, String> formData;
    /**
     * cache all request data include:form data and query param
     */
    private MultiValueMap<String, String> allRequestData = new LinkedMultiValueMap<>(0);
}
