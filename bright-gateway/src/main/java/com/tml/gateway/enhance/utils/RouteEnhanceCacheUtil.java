package com.tml.gateway.enhance.utils;


import com.tml.common.core.entity.constant.BrightConstant;

/**
 * @description 
 * @author JacksonTu
 * @since 2020-08-10 20:30
 * @version 1.0
 */
public class RouteEnhanceCacheUtil {

    private static final String BLACKLIST_CHACHE_KEY_PREFIX = "bright:route:blacklist:";
    private static final String RATELIMIT_CACHE_KEY_PREFIX = "bright:route:ratelimit:";
    private static final String RATELIMIT_COUNT_KEY_PREFIX = "bright:route:ratelimit:cout:";

    public static String getBlackListCacheKey(String ip) {
        if (BrightConstant.LOCALHOST.equalsIgnoreCase(ip)) {
            ip = BrightConstant.LOCALHOST_IP;
        }
        return String.format("%s%s", BLACKLIST_CHACHE_KEY_PREFIX, ip);
    }

    public static String getBlackListCacheKey() {
        return String.format("%sall", BLACKLIST_CHACHE_KEY_PREFIX);
    }

    public static String getRateLimitCacheKey(String uri, String method) {
        return String.format("%s%s:%s", RATELIMIT_CACHE_KEY_PREFIX, uri, method);
    }

    public static String getRateLimitCountKey(String uri, String ip) {
        return String.format("%s%s:%s", RATELIMIT_COUNT_KEY_PREFIX, uri, ip);
    }
}
