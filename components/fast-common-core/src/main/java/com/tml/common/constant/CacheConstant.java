package com.tml.common.constant;

/**
 * @Description 缓存常量
 * @Author TuMingLong
 * @Date 2020/4/6 17:35
 */
public interface CacheConstant {
    /**
     * 字典信息缓存
     */
     String SYS_DICT_CACHE = "sys:cache:dict";
    /**
     * 表字典信息缓存
     */
     String SYS_DICT_TABLE_CACHE = "sys:cache:dictTable";

    /**
     * 数据权限配置缓存
     */
     String SYS_DATA_PERMISSIONS_CACHE = "sys:cache:permission:dataRules";
    /**
     * 用户信息缓存
     */
     String SYS_USERS_CACHE = "sys:cache:user";
    /**
     * 用户拥有角色信息缓存
     */
    String SYS_USERS_ROLES_CACHE = "sys:cache:user:role";
    /**
     * 用户拥有权限信息缓存
     */
    String SYS_USERS_PERMS_CACHE = "sys:cache:user:perms";

    /**
     * 全部部门信息缓存
     */
     String SYS_DEPARTS_CACHE = "sys:cache:depart:allData";


    /**
     * 全部部门ids缓存
     */
     String SYS_DEPART_IDS_CACHE = "sys:cache:depart:allIds";

    /**
     * 测试缓存key
     */
     String TEST_DEMO_CACHE = "test:demo";

     String CLIENT_DETAILS_KEY="oauth:client:details";

    String SCAN_API_RESOURCE_KEY_PREFIX = "scan_api_resource:";
}
