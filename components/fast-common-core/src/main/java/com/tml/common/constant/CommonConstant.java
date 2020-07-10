package com.tml.common.constant;

/**
 * @Description 公共常量
 * @Author TuMingLong
 * @Date 2020/3/31 11:33
 */
public interface CommonConstant {
    /**
     * 超级管理员ID
     */
    int SUPER_ADMIN = 1;

    /**
     * 客户端ID KEY
     */
    String SIGN_CLIENT_ID_KEY = "clientId";

    /**
     * 客户端秘钥 KEY
     */
    String SIGN_CLIENT_SECRET_KEY = "clientSecret";

    /**
     * 随机字符串 KEY
     */
    String SIGN_NONCE_KEY = "nonce";
    /**
     * 时间戳 KEY
     */
    String SIGN_TIMESTAMP_KEY = "timestamp";
    /**
     * 签名类型 KEY
     */
    String SIGN_SIGN_TYPE_KEY = "signType";
    /**
     * 签名结果 KEY
     */
    String SIGN_SIGN_KEY = "sign";

    /**
     * 账号状态
     * 0:正常、1:锁定
     */
    public final static int ACCOUNT_STATUS_NORMAL = 0;

    public final static int ACCOUNT_STATUS_LOCKED = 1;
    /**
     * 默认最小页码
     */
    long MIN_PAGE = 0;
    /**
     * 最大显示条数
     */
    long MAX_LIMIT = 1000;
    /**
     * 默认页码
     */
    long DEFAULT_PAGE = 1;
    /**
     * 默认显示条数
     */
    long DEFAULT_LIMIT = 10;
    /**
     * 页码 KEY
     */
    String PAGE_KEY = "page";
    /**
     * 显示条数 KEY
     */
    String PAGE_LIMIT_KEY = "limit";
    /**
     * 排序字段 KEY
     */
    String PAGE_SORT_KEY = "sort";
    /**
     * 排序方向 KEY
     */
    String PAGE_ORDER_KEY = "order";
    /**
     * 租户ID
     */
    String TENANT_KEY = "tenantId";

    /**
     * Java默认临时目录
     */
    String JAVA_TEMP_DIR = "java.io.tmpdir";

    /**
     * 默认接口分类
     */
    String DEFAULT_API_CATEGORY = "default";

    /**
     * 状态:0-无效 1-有效
     */
    int ENABLED = 1;
    int DISABLED = 0;

}
