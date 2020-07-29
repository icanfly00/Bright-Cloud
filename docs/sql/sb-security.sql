/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50730
 Source Host           : localhost:3306
 Source Schema         : sb-security

 Target Server Type    : MySQL
 Target Server Version : 50730
 File Encoding         : 65001

 Date: 29/07/2020 20:16:58
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for clientdetails
-- ----------------------------
DROP TABLE IF EXISTS `clientdetails`;
CREATE TABLE `clientdetails`  (
  `appId` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `resourceIds` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `appSecret` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `scope` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `grantTypes` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `redirectUrl` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `authorities` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `access_token_validity` int(11) NULL DEFAULT NULL,
  `refresh_token_validity` int(11) NULL DEFAULT NULL,
  `additionalInformation` varchar(4096) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `autoApproveScopes` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`appId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of clientdetails
-- ----------------------------

-- ----------------------------
-- Table structure for gateway_black_list
-- ----------------------------
DROP TABLE IF EXISTS `gateway_black_list`;
CREATE TABLE `gateway_black_list`  (
  `id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `ip` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '黑名单IP',
  `request_uri` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求URI',
  `request_method` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求方法',
  `limit_from` datetime(0) NULL DEFAULT NULL COMMENT '限制时间起',
  `limit_to` datetime(0) NULL DEFAULT NULL COMMENT '限制时间止',
  `location` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'IP对应地址',
  `status` tinyint(1) NULL DEFAULT NULL COMMENT '状态(0-关闭，1-开启)',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `create_user_id` int(11) NULL DEFAULT NULL COMMENT '创建用户',
  `update_user_id` int(11) NULL DEFAULT NULL COMMENT '更新用户',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '黑名单' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of gateway_black_list
-- ----------------------------
INSERT INTO `gateway_black_list` VALUES (9, '127.0.0.1', '/**/actuator/**', 'All', '2020-07-30 00:30:00', '2020-07-30 23:59:59', NULL, 1, '2020-07-28 21:52:46', '2020-07-28 21:52:46', 1, 1);
INSERT INTO `gateway_black_list` VALUES (10, '192.168.0.106', '/**/actuator/**', 'All', '2020-07-30 00:30:00', '2020-07-30 23:59:59', NULL, 1, '2020-07-28 21:52:46', '2020-07-28 21:52:46', 1, 1);

-- ----------------------------
-- Table structure for gateway_black_list_log
-- ----------------------------
DROP TABLE IF EXISTS `gateway_black_list_log`;
CREATE TABLE `gateway_black_list_log`  (
  `id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `ip` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '黑名单IP',
  `request_uri` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求URI',
  `request_method` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求方法',
  `location` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'IP对应地址',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '拦截时间点',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '黑名单日志' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of gateway_black_list_log
-- ----------------------------

-- ----------------------------
-- Table structure for gateway_dynamic_route
-- ----------------------------
DROP TABLE IF EXISTS `gateway_dynamic_route`;
CREATE TABLE `gateway_dynamic_route`  (
  `id` bigint(19) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `route_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '路由名称',
  `route_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '路由Id',
  `route_uri` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '路由规则转发的uri',
  `route_order` int(11) NOT NULL DEFAULT 0 COMMENT '路由的执行顺序',
  `predicates` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '断言字符串集合，字符串结构：[{\r\n                \"name\":\"Path\",\r\n                \"args\":{\r\n                   \"pattern\" : \"/api/**\"\r\n                }\r\n              }]',
  `filters` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '过滤器字符串集合，字符串结构：{\r\n              	\"name\":\"StripPrefix\",\r\n              	 \"args\":{\r\n              	 	\"_genkey_0\":\"1\"\r\n              	 }\r\n              }',
  `enable` tinyint(1) NOT NULL COMMENT '状态：0-不可用；1-可用',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `create_user_id` bigint(19) NULL DEFAULT NULL COMMENT '创建人ID',
  `update_user_id` bigint(19) NULL DEFAULT NULL COMMENT '修改人ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `route_id`(`route_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1288004131295485956 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '动态路由配置表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of gateway_dynamic_route
-- ----------------------------
INSERT INTO `gateway_dynamic_route` VALUES (2, '系统管理服务', 'fast-system-service', 'lb://fast-system-service', 1, '[{\"name\":\"Path\",\"args\":{\"pattern\":\"/system/**\"}}]', '', 1, '2020-07-29 17:22:49', '2020-07-28 14:51:29', 1, 1);

-- ----------------------------
-- Table structure for gateway_route_limit_rule
-- ----------------------------
DROP TABLE IF EXISTS `gateway_route_limit_rule`;
CREATE TABLE `gateway_route_limit_rule`  (
  `id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `request_uri` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求URI',
  `request_method` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求方法',
  `limit_from` datetime(0) NULL DEFAULT NULL COMMENT '限制时间起',
  `limit_to` datetime(0) NULL DEFAULT NULL COMMENT '限制时间止',
  `count` int(19) NULL DEFAULT NULL COMMENT '次数',
  `interval_sec` int(19) NULL DEFAULT NULL COMMENT '时间周期，单位秒',
  `status` tinyint(1) NULL DEFAULT NULL COMMENT '状态(0-关闭，1-开启)',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `create_user_id` int(11) NULL DEFAULT NULL COMMENT '创建用户',
  `update_user_id` int(11) NULL DEFAULT NULL COMMENT '更新用户',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '限流规则' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of gateway_route_limit_rule
-- ----------------------------
INSERT INTO `gateway_route_limit_rule` VALUES (3, '/auth/**', 'GET', '2020-07-30 00:30:00', '2020-07-30 23:59:59', 3, 10, 1, '2020-07-29 10:26:49', '2020-07-29 10:26:49', 1, 1);
INSERT INTO `gateway_route_limit_rule` VALUES (4, '/gateway/**', 'GET', '2020-07-30 00:30:00', '2020-07-30 23:59:59', 3, 10, 1, '2020-07-29 10:26:50', '2020-07-29 10:26:50', 1, 1);

-- ----------------------------
-- Table structure for gateway_route_limit_rule_log
-- ----------------------------
DROP TABLE IF EXISTS `gateway_route_limit_rule_log`;
CREATE TABLE `gateway_route_limit_rule_log`  (
  `id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `ip` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '黑名单IP',
  `request_uri` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求URI',
  `request_method` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求方法',
  `location` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'IP对应地址',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '拦截时间点',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '限流规则日志' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of gateway_route_limit_rule_log
-- ----------------------------

-- ----------------------------
-- Table structure for gateway_route_log
-- ----------------------------
DROP TABLE IF EXISTS `gateway_route_log`;
CREATE TABLE `gateway_route_log`  (
  `id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `ip` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '黑名单IP',
  `request_uri` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求URI',
  `request_method` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求方法',
  `target_uri` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '目标URI',
  `target_server` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '目标服务',
  `location` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'IP对应地址',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '拦截时间点',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '网关日志' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of gateway_route_log
-- ----------------------------
INSERT INTO `gateway_route_log` VALUES (2, '127.0.0.1', '/system/sys/user/findSecurityUserByUsername', 'GET', '/system/sys/user/findSecurityUserByUsername', 'fast-system-service', '0|0|0|内网IP|内网IP', '2020-07-29 14:33:30');

-- ----------------------------
-- Table structure for oauth_access_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_access_token`;
CREATE TABLE `oauth_access_token`  (
  `token_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `token` blob NULL,
  `authentication_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `client_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `authentication` blob NULL,
  `refresh_token` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`authentication_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oauth_access_token
-- ----------------------------

-- ----------------------------
-- Table structure for oauth_approvals
-- ----------------------------
DROP TABLE IF EXISTS `oauth_approvals`;
CREATE TABLE `oauth_approvals`  (
  `userId` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `clientId` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `scope` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `expiresAt` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `lastModifiedAt` timestamp(0) NOT NULL DEFAULT '0000-00-00 00:00:00',
  `create_time` timestamp(0) NOT NULL DEFAULT '0000-00-00 00:00:00'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oauth_approvals
-- ----------------------------

-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details`  (
  `client_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `resource_ids` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `client_secret` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `scope` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `authorized_grant_types` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `web_server_redirect_uri` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `authorities` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `access_token_validity` int(11) NULL DEFAULT NULL,
  `refresh_token_validity` int(11) NULL DEFAULT NULL,
  `additional_information` varchar(4096) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `autoapprove` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`client_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oauth_client_details
-- ----------------------------
INSERT INTO `oauth_client_details` VALUES ('client_1', 'oauth2-resource', '$2a$10$uvVjwDE7sYtPlI5Gy5v9H.jj9BigZjokirpGeTIZTiZ1uhu3CJasu', 'all', 'authorization_code,password,client_credentials,implicit,refresh_token', 'http://www.baidu.com', NULL, NULL, NULL, NULL, 'false', '2020-05-20 10:06:14');
INSERT INTO `oauth_client_details` VALUES ('client_2', 'oauth2-resource', '$2a$10$uvVjwDE7sYtPlI5Gy5v9H.jj9BigZjokirpGeTIZTiZ1uhu3CJasu', 'all', 'password,refresh_token', 'http://www.baidu.com', NULL, NULL, NULL, NULL, 'false', '2020-05-20 10:06:14');
INSERT INTO `oauth_client_details` VALUES ('client_3', 'oauth2-resource', '$2a$10$uvVjwDE7sYtPlI5Gy5v9H.jj9BigZjokirpGeTIZTiZ1uhu3CJasu', 'all', 'client_credentials,refresh_token', 'http://www.baidu.com', NULL, NULL, NULL, NULL, 'false', '2020-05-20 10:06:14');

-- ----------------------------
-- Table structure for oauth_client_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_token`;
CREATE TABLE `oauth_client_token`  (
  `token_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `token` blob NULL,
  `authentication_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `client_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`authentication_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oauth_client_token
-- ----------------------------

-- ----------------------------
-- Table structure for oauth_code
-- ----------------------------
DROP TABLE IF EXISTS `oauth_code`;
CREATE TABLE `oauth_code`  (
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `authentication` blob NULL,
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0)
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oauth_code
-- ----------------------------

-- ----------------------------
-- Table structure for oauth_refresh_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_refresh_token`;
CREATE TABLE `oauth_refresh_token`  (
  `token_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `token` blob NULL,
  `authentication` blob NULL,
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0)
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oauth_refresh_token
-- ----------------------------

-- ----------------------------
-- Table structure for oms_item
-- ----------------------------
DROP TABLE IF EXISTS `oms_item`;
CREATE TABLE `oms_item`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商品主键ID',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品名',
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品编号',
  `stock` bigint(20) NULL DEFAULT NULL COMMENT '库存',
  `purchase_time` date NULL DEFAULT NULL COMMENT '采购时间',
  `is_active` int(11) NULL DEFAULT 1 COMMENT '是否有效（1=是；0=否）',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oms_item
-- ----------------------------
INSERT INTO `oms_item` VALUES (6, 'Java编程思想', 'book10010', 1000, '2019-05-18', 1, '2019-05-18 21:11:23', NULL);
INSERT INTO `oms_item` VALUES (7, 'Spring实战第四版', 'book10011', 2000, '2019-05-18', 1, '2019-05-18 21:11:23', NULL);
INSERT INTO `oms_item` VALUES (8, '深入分析JavaWeb', 'book10012', 2000, '2019-05-18', 1, '2019-05-18 21:11:23', NULL);

-- ----------------------------
-- Table structure for oms_item_kill
-- ----------------------------
DROP TABLE IF EXISTS `oms_item_kill`;
CREATE TABLE `oms_item_kill`  (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `item_id` int(11) NULL DEFAULT NULL COMMENT '商品id',
  `total` int(11) NULL DEFAULT NULL COMMENT '可被秒杀的总数',
  `start_time` datetime(0) NULL DEFAULT NULL COMMENT '秒杀开始时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '秒杀结束时间',
  `is_active` tinyint(11) NULL DEFAULT 1 COMMENT '是否有效（1=是；0=否）',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '待秒杀商品表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oms_item_kill
-- ----------------------------
INSERT INTO `oms_item_kill` VALUES (1, 6, 98, '2020-04-02 00:00:07', '2020-04-30 23:59:59', 1, '2020-04-02 20:52:42');
INSERT INTO `oms_item_kill` VALUES (2, 7, 99, '2019-06-01 00:00:19', '2020-04-30 23:59:59', 1, '2020-04-02 20:52:41');
INSERT INTO `oms_item_kill` VALUES (3, 8, 96, '2020-04-02 00:00:26', '2020-04-30 23:59:59', 1, '2020-04-02 20:52:41');

-- ----------------------------
-- Table structure for oms_item_kill_order
-- ----------------------------
DROP TABLE IF EXISTS `oms_item_kill_order`;
CREATE TABLE `oms_item_kill_order`  (
  `code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '秒杀成功生成的订单编号',
  `item_id` int(11) NULL DEFAULT NULL COMMENT '商品id',
  `kill_id` int(11) NULL DEFAULT NULL COMMENT '秒杀id',
  `user_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户id',
  `status` tinyint(4) NULL DEFAULT -1 COMMENT '秒杀结果: -1无效  0成功(未付款)  1已付款  2已取消',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '秒杀成功订单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oms_item_kill_order
-- ----------------------------
INSERT INTO `oms_item_kill_order` VALUES ('1245911999318466560', 8, 3, '10', -1, '2020-04-03 11:12:23');
INSERT INTO `oms_item_kill_order` VALUES ('1245924188716670976', 8, 3, '8', -1, '2020-04-03 12:00:49');
INSERT INTO `oms_item_kill_order` VALUES ('1245928148169007104', 6, 1, '8', -1, '2020-04-03 12:16:33');

-- ----------------------------
-- Table structure for oms_random_code
-- ----------------------------
DROP TABLE IF EXISTS `oms_random_code`;
CREATE TABLE `oms_random_code`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_code`(`code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oms_random_code
-- ----------------------------

-- ----------------------------
-- Table structure for sys_api
-- ----------------------------
DROP TABLE IF EXISTS `sys_api`;
CREATE TABLE `sys_api`  (
  `api_id` bigint(19) NOT NULL COMMENT '接口ID',
  `api_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '接口编码',
  `api_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '接口名称',
  `api_category` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT 'default' COMMENT '接口分类:default-默认分类',
  `api_desc` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '资源描述',
  `request_method` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '请求方式',
  `content_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '响应类型',
  `service_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '服务ID',
  `path` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '请求路径',
  `priority` bigint(20) NOT NULL DEFAULT 0 COMMENT '优先级',
  `status` tinyint(3) NOT NULL DEFAULT 1 COMMENT '状态:0-无效 1-有效',
  `create_time` datetime(0) NOT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `is_persist` tinyint(3) NOT NULL DEFAULT 0 COMMENT '保留数据0-否 1-是 不允许删除',
  `is_auth` tinyint(3) NOT NULL DEFAULT 1 COMMENT '是否需要认证: 0-无认证 1-身份认证 默认:1',
  `is_open` tinyint(3) NOT NULL DEFAULT 0 COMMENT '是否公开: 0-内部的 1-公开的',
  `class_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '类名',
  `method_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '方法名',
  PRIMARY KEY (`api_id`) USING BTREE,
  UNIQUE INDEX `api_code`(`api_code`) USING BTREE,
  UNIQUE INDEX `api_id`(`api_id`) USING BTREE,
  INDEX `service_id`(`service_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '系统API接口' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of sys_api
-- ----------------------------
INSERT INTO `sys_api` VALUES (1, 'all', '全部', 'default', '所有请求', 'get,post', NULL, 'fast-gateway-service', '/**', 0, 1, '2019-03-07 21:52:17', '2019-03-14 21:41:28', 1, 1, 1, NULL, NULL);
INSERT INTO `sys_api` VALUES (2, 'actuator', '监控端点', 'default', '监控端点', 'post', NULL, 'fast-gateway-service', '/actuator/**', 0, 1, '2019-03-07 21:52:17', '2019-03-14 21:41:28', 1, 1, 1, NULL, NULL);
INSERT INTO `sys_api` VALUES (1288351187168071681, '3b314778e33c5c192f44fd99a6481a41', '检查客户端ID', 'default', '检查客户端ID', 'GET', NULL, 'fast-system-service', '/client/check/{clientId}', 0, 1, '2020-07-29 13:50:34', '2020-07-29 18:49:26', 1, 1, 0, 'com.tml.system.controller.OauthClientDetailsController', 'checkUserName');
INSERT INTO `sys_api` VALUES (1288351187344232450, 'a2ca522442bc988faf01a21d2be57651', '获取客户端密码明文', 'default', '获取客户端密码明文', 'GET', NULL, 'fast-system-service', '/client/secret/{clientId}', 0, 1, '2020-07-29 13:50:34', '2020-07-29 18:49:26', 1, 1, 0, 'com.tml.system.controller.OauthClientDetailsController', 'getOriginClientSecret');
INSERT INTO `sys_api` VALUES (1288351187440701442, '736e372c15004332f0e86ba686edd2d6', '客户端信息分页列表', 'default', '客户端信息分页列表', 'GET', NULL, 'fast-system-service', '/client/list', 0, 1, '2020-07-29 13:50:34', '2020-07-29 18:49:26', 1, 1, 0, 'com.tml.system.controller.OauthClientDetailsController', 'oauthClientDetailsList');
INSERT INTO `sys_api` VALUES (1288351187520393217, '51877c66df6969cb296c6543761d4b23', '修改客户端', 'default', '修改客户端', 'PUT', NULL, 'fast-system-service', '/client/update', 0, 1, '2020-07-29 13:50:34', '2020-07-29 18:49:26', 1, 1, 0, 'com.tml.system.controller.OauthClientDetailsController', 'updateOauthClientDetails');
INSERT INTO `sys_api` VALUES (1288351187591696386, '23a3f4be93cdd093e2f51359de0b1d62', '新增客户端', 'default', '新增客户端', 'POST', NULL, 'fast-system-service', '/client/add', 0, 1, '2020-07-29 13:50:34', '2020-07-29 18:49:26', 1, 1, 0, 'com.tml.system.controller.OauthClientDetailsController', 'addOauthClientDetails');
INSERT INTO `sys_api` VALUES (1288351187667193857, '070ed98790be54cdd3133a8737ac01c1', '删除客户端', 'default', '删除客户端', 'DELETE', NULL, 'fast-system-service', '/client/delete', 0, 1, '2020-07-29 13:50:34', '2020-07-29 18:49:26', 1, 1, 0, 'com.tml.system.controller.OauthClientDetailsController', 'deleteOauthClientDetails');
INSERT INTO `sys_api` VALUES (1288351187734302721, '9c57e44696fef150b97f5485aa221b03', '记录登录日志', 'default', '记录登录日志', 'GET', NULL, 'fast-system-service', '/sys/user/success', 0, 1, '2020-07-29 13:50:34', '2020-07-29 18:49:26', 1, 1, 0, 'com.tml.system.controller.SysUserController', 'loginSuccess');
INSERT INTO `sys_api` VALUES (1288351187809800194, '7490bc2ec9a8e497c6fb61fa47779ce9', '根据用户Id查询数据权限', 'default', '根据用户Id查询数据权限', 'GET', NULL, 'fast-system-service', '/sys/user/findDataPermsByUserId', 0, 1, '2020-07-29 13:50:34', '2020-07-29 18:49:26', 1, 0, 0, 'com.tml.system.controller.SysUserController', 'findDataPermsByUserId');
INSERT INTO `sys_api` VALUES (1288351187876909058, 'eeede8ece30c69e4886a1dc785844dc8', '根据用户Id查询角色集合', 'default', '根据用户Id查询角色集合', 'GET', NULL, 'fast-system-service', '/sys/user/findRoleIdByUserId', 0, 1, '2020-07-29 13:50:34', '2020-07-29 18:49:26', 1, 0, 0, 'com.tml.system.controller.SysUserController', 'findRoleIdByUserId');
INSERT INTO `sys_api` VALUES (1288351187948212225, '544bec30e6c547a9713a4153397f3e67', '根据用户手机号获取登录用户信息', 'default', '根据用户手机号获取登录用户信息', 'GET', NULL, 'fast-system-service', '/sys/user/findSecurityUserByPhone', 0, 1, '2020-07-29 13:50:34', '2020-07-29 18:49:26', 1, 0, 0, 'com.tml.system.controller.SysUserController', 'findSecurityUserByPhone');
INSERT INTO `sys_api` VALUES (1288351188015321090, '0eea3878d32ee24fa1a070d70019e416', '根据用户名获取登录用户信息', 'default', '根据用户名获取登录用户信息', 'GET', NULL, 'fast-system-service', '/sys/user/findSecurityUserByUsername', 0, 1, '2020-07-29 13:50:34', '2020-07-29 18:49:26', 1, 0, 0, 'com.tml.system.controller.SysUserController', 'findSecurityUserByUsername');
INSERT INTO `sys_api` VALUES (1288351188128567297, 'b3af28de64bf8ee21455c7e84f5b79d1', '根据用户Id查询权限', 'default', '根据用户Id查询权限', 'GET', NULL, 'fast-system-service', '/sys/user/findPermsByUserId', 0, 1, '2020-07-29 13:50:34', '2020-07-29 18:49:26', 1, 0, 0, 'com.tml.system.controller.SysUserController', 'findPermsByUserId');
INSERT INTO `sys_api` VALUES (1288351188304728065, 'c02545151be2a464f2944bec1a7cd998', 'uiConfiguration', 'default', '', '', NULL, 'fast-system-service', '/swagger-resources/configuration/ui', 0, 1, '2020-07-29 13:50:34', '2020-07-29 18:49:26', 1, 0, 0, 'springfox.documentation.swagger.web.ApiResourceController', 'uiConfiguration');
INSERT INTO `sys_api` VALUES (1288351188376031233, '9296f0b873c778f885820635841aa3fd', 'swaggerResources', 'default', '', '', NULL, 'fast-system-service', '/swagger-resources', 0, 1, '2020-07-29 13:50:34', '2020-07-29 18:49:26', 1, 0, 0, 'springfox.documentation.swagger.web.ApiResourceController', 'swaggerResources');
INSERT INTO `sys_api` VALUES (1288351188426362881, 'fdbca130083c452274fefa40b00146a6', 'securityConfiguration', 'default', '', '', NULL, 'fast-system-service', '/swagger-resources/configuration/security', 0, 1, '2020-07-29 13:50:34', '2020-07-29 18:49:26', 1, 0, 0, 'springfox.documentation.swagger.web.ApiResourceController', 'securityConfiguration');
INSERT INTO `sys_api` VALUES (1288351188514443265, 'c1954176f5d8935238bd12cc668348fc', 'errorHtml', 'default', '', '', NULL, 'fast-system-service', '/error', 0, 1, '2020-07-29 13:50:34', '2020-07-29 18:49:26', 1, 1, 0, 'org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController', 'errorHtml');
INSERT INTO `sys_api` VALUES (1288351363282702338, 'b4857e6cc7ae11744bd703880327ab93', 'login', 'default', '', '', NULL, 'fast-uaa-service', '/login', 0, 1, '2020-07-29 13:51:16', '2020-07-29 16:55:02', 1, 0, 0, 'com.tml.uaa.controller.IndexController', 'login');
INSERT INTO `sys_api` VALUES (1288351363345616898, '4f19dccf7fc6483f783c7aed460927d3', 'welcome', 'default', '', '', NULL, 'fast-uaa-service', '/,/welcome', 0, 1, '2020-07-29 13:51:16', '2020-07-29 16:55:02', 1, 0, 0, 'com.tml.uaa.controller.IndexController', 'welcome');
INSERT INTO `sys_api` VALUES (1288351363408531458, '947c2db96141a109822e66f9d64ca0c1', '获取当前登录用户信息-SSO单点登录', 'default', '获取当前登录用户信息-SSO单点登录', 'GET', NULL, 'fast-uaa-service', '/current/user/sso', 0, 1, '2020-07-29 13:51:16', '2020-07-29 16:55:02', 1, 1, 0, 'com.tml.uaa.controller.LoginController', 'principal');
INSERT INTO `sys_api` VALUES (1288351363471446018, '73ff31ef4020ecaa76e19d934ab22381', '获取当前登录用户信息', 'default', '获取当前登录用户信息', 'GET', NULL, 'fast-uaa-service', '/current/user', 0, 1, '2020-07-29 13:51:16', '2020-07-29 16:55:02', 1, 1, 0, 'com.tml.uaa.controller.LoginController', 'getUserProfile');
INSERT INTO `sys_api` VALUES (1288351363534360578, 'c25a04b149316180872f40ba55d17b9d', 'securityConfiguration', 'default', '', '', NULL, 'fast-uaa-service', '/swagger-resources/configuration/security', 0, 1, '2020-07-29 13:51:16', '2020-07-29 16:55:02', 1, 0, 0, 'springfox.documentation.swagger.web.ApiResourceController', 'securityConfiguration');
INSERT INTO `sys_api` VALUES (1288351363601469442, 'c420402979d1ae57ac6c92295ce10212', 'swaggerResources', 'default', '', '', NULL, 'fast-uaa-service', '/swagger-resources', 0, 1, '2020-07-29 13:51:16', '2020-07-29 16:55:02', 1, 0, 0, 'springfox.documentation.swagger.web.ApiResourceController', 'swaggerResources');
INSERT INTO `sys_api` VALUES (1288351363664384002, '38965f6d1bdadc7bab2e13da4f40b368', 'uiConfiguration', 'default', '', '', NULL, 'fast-uaa-service', '/swagger-resources/configuration/ui', 0, 1, '2020-07-29 13:51:16', '2020-07-29 16:55:02', 1, 0, 0, 'springfox.documentation.swagger.web.ApiResourceController', 'uiConfiguration');
INSERT INTO `sys_api` VALUES (1288351363727298562, 'c012ca98162c2f2ee7344cc3bd0ac2f2', 'errorHtml', 'default', '', '', NULL, 'fast-uaa-service', '/error', 0, 1, '2020-07-29 13:51:16', '2020-07-29 16:55:02', 1, 1, 0, 'org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController', 'errorHtml');

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `dept_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '部门主键ID',
  `enterprise_id` int(11) NULL DEFAULT NULL COMMENT '企业ID',
  `dept_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '部门名称',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '0' COMMENT '是否删除  -1：已删除  0：正常',
  `parent_id` int(11) NULL DEFAULT NULL COMMENT '上级部门',
  `tenant_id` int(11) NULL DEFAULT NULL COMMENT '租户id',
  PRIMARY KEY (`dept_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '部门管理' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (4, 1, '某某集团', 0, '2019-04-21 22:53:33', '2020-06-04 17:51:06', '0', 0, 1);
INSERT INTO `sys_dept` VALUES (5, 1, '上海分公司', 0, '2019-04-21 22:53:57', '2020-06-04 17:51:06', '0', 4, 1);
INSERT INTO `sys_dept` VALUES (6, 1, '研发部', 0, '2019-04-21 22:54:10', '2020-06-04 17:51:07', '0', 5, 1);
INSERT INTO `sys_dept` VALUES (7, 1, '财务部', 0, '2019-04-21 22:54:46', '2020-06-04 17:51:07', '0', 5, 1);
INSERT INTO `sys_dept` VALUES (12, 1, '市场部', 4, '2019-04-30 14:31:46', '2020-06-04 17:51:07', '0', 5, 1);
INSERT INTO `sys_dept` VALUES (14, 1, '人事部', 0, '2019-12-14 15:25:17', '2020-06-04 17:51:07', '0', 5, 1);
INSERT INTO `sys_dept` VALUES (16, 1, '北京分公司', 0, '2019-12-14 15:26:35', '2020-06-04 17:51:07', '0', 4, 1);
INSERT INTO `sys_dept` VALUES (17, 1, '人事部', 0, '2019-12-14 15:33:38', '2020-06-04 17:51:07', '1', 16, 1);
INSERT INTO `sys_dept` VALUES (18, 1, '111', 0, '2019-12-14 21:36:42', '2020-06-04 17:51:07', '1', 4, 1);
INSERT INTO `sys_dept` VALUES (19, 1, '111', 0, '2019-12-14 21:40:26', '2020-06-04 17:51:07', '1', 16, 1);
INSERT INTO `sys_dept` VALUES (20, 1, '11', 0, '2019-12-14 21:42:24', '2020-06-04 17:51:07', '1', 17, 1);
INSERT INTO `sys_dept` VALUES (21, 1, '默认部门', 0, '2019-12-17 21:30:31', '2020-06-04 17:51:07', '0', 0, 2);

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `dict_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  `dict_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典编码',
  `description` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序（升序）',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `remark` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标记',
  `tenant_id` int(11) NULL DEFAULT NULL COMMENT '租户Id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `sys_dict_del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES (1, '用户状态', 'lock_flag', '用户是否正常还是锁定', NULL, '2019-12-16 13:35:43', '2019-12-17 21:24:29', NULL, '0', 1);
INSERT INTO `sys_dict` VALUES (2, '菜单类型', 'menu_type', '菜单类型 （类型   0：目录   1：菜单   2：按钮）', NULL, '2019-12-16 13:42:46', '2019-12-17 21:24:29', NULL, '0', 1);

-- ----------------------------
-- Table structure for sys_dict_item
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_item`;
CREATE TABLE `sys_dict_item`  (
  `id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `dict_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字典id',
  `item_text` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字典项文本',
  `item_value` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字典项值',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（1启用 0不启用）',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_table_dict_id`(`dict_id`) USING BTREE,
  INDEX `index_table_dict_status`(`status`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典详情表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dict_item
-- ----------------------------
INSERT INTO `sys_dict_item` VALUES ('0c8f86876bfc7c59a5236010fdcaa07e', '2', '目录', '1', '', NULL, '2019-12-16 13:57:39', '2019-12-16 13:57:39');
INSERT INTO `sys_dict_item` VALUES ('3fe7ad23294384de45197f3379b8d658', '1', '锁定', '1', '0-正常，1-锁定', NULL, '2019-12-16 13:39:56', '2019-12-16 13:39:56');
INSERT INTO `sys_dict_item` VALUES ('5ace75b3caf31b86efa50430954d624f', '2', '按钮', '3', '', NULL, '2019-12-16 13:57:55', '2019-12-16 13:57:55');
INSERT INTO `sys_dict_item` VALUES ('6ea98d652a06220c99b9468ead68e6f9', '2', '菜单', '1', '', NULL, '2019-12-16 13:57:48', '2019-12-16 13:57:48');
INSERT INTO `sys_dict_item` VALUES ('f27a639dee243eef860f453c2ab8547e', '1', '正常', '0', '0-正常，1-锁定', NULL, '2019-12-16 13:39:45', '2019-12-16 13:39:45');

-- ----------------------------
-- Table structure for sys_enterprise
-- ----------------------------
DROP TABLE IF EXISTS `sys_enterprise`;
CREATE TABLE `sys_enterprise`  (
  `id` int(11) NOT NULL COMMENT '主键ID',
  `enterprise_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '企业名称',
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '企业识别码',
  `legal_person` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '法人',
  `industry_code` bigint(19) NULL DEFAULT NULL COMMENT '行业编码',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址',
  `telephone` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话',
  `status` tinyint(2) NULL DEFAULT NULL COMMENT '状态(0-正常，1-删除)',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `create_user_id` int(11) NULL DEFAULT NULL COMMENT '创建用户',
  `update_user_id` int(11) NULL DEFAULT NULL COMMENT '更新用户',
  `del_flag` int(2) NULL DEFAULT NULL COMMENT '是否删除  -1：已删除  0：正常',
  `tenant_id` int(11) NULL DEFAULT NULL COMMENT '租户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '企业表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_enterprise
-- ----------------------------
INSERT INTO `sys_enterprise` VALUES (1, 'test', NULL, NULL, NULL, NULL, NULL, 0, '2020-06-04 17:48:31', '2020-06-04 17:49:14', 4, 4, 0, 1);
INSERT INTO `sys_enterprise` VALUES (2, 'test2', NULL, NULL, NULL, NULL, NULL, 0, '2020-06-04 17:48:33', '2020-06-04 17:49:15', 4, 4, 0, 1);
INSERT INTO `sys_enterprise` VALUES (3, 'test3', NULL, NULL, NULL, NULL, NULL, 0, '2020-06-04 17:48:35', '2020-06-04 17:49:18', 4, 4, 0, 1);
INSERT INTO `sys_enterprise` VALUES (4, 'test4', NULL, NULL, NULL, NULL, NULL, 0, '2020-06-04 17:48:37', '2020-06-04 17:49:20', 4, 4, 0, 1);

-- ----------------------------
-- Table structure for sys_job
-- ----------------------------
DROP TABLE IF EXISTS `sys_job`;
CREATE TABLE `sys_job`  (
  `job_id` int(11) NOT NULL COMMENT '岗位主键id',
  `job_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '岗位名称',
  `dept_id` int(11) NULL DEFAULT NULL COMMENT '部门id',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '0' COMMENT '是否删除  -1：已删除  0：正常',
  `tenant_id` int(11) NULL DEFAULT NULL COMMENT '租户id',
  PRIMARY KEY (`job_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '岗位表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_job
-- ----------------------------

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `type` int(11) NULL DEFAULT NULL COMMENT '操作类型 1 操作记录2异常记录',
  `ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '操作IP',
  `location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '操作地点',
  `os` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '操作系统',
  `browser` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '浏览器',
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '操作人',
  `description` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '操作描述',
  `action_method` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '请求方法',
  `action_url` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `params` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '请求参数',
  `class_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '类路径',
  `request_method` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '请求方法',
  `start_time` timestamp(0) NULL DEFAULT NULL COMMENT '开始时间',
  `finish_time` timestamp(0) NULL DEFAULT NULL COMMENT '完成时间',
  `consuming_time` bigint(20) NULL DEFAULT NULL COMMENT '消耗时间',
  `ex_desc` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '异常详情信息',
  `ex_detail` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '异常描述',
  `tenant_id` int(11) NULL DEFAULT NULL COMMENT '租户id',
  `del_flag` int(2) NULL DEFAULT NULL COMMENT '是否删除  -1：已删除  0：正常',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_type`(`type`) USING BTREE COMMENT '日志类型'
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '系统日志' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '菜单名称',
  `perms` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '菜单权限标识',
  `path` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '前端跳转URL',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '菜单组件',
  `parent_id` int(11) NULL DEFAULT NULL COMMENT '父菜单ID',
  `icon` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '图标',
  `sort` int(11) NULL DEFAULT 1 COMMENT '排序',
  `type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '菜单类型 （类型   0：目录   1：菜单   2：按钮）',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '0' COMMENT '逻辑删除标记(0--正常 1--删除)',
  `is_frame` tinyint(1) NULL DEFAULT NULL COMMENT '是否为外链',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 73 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '菜单权限表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '权限管理', '', 'admin', '', 0, 'authority', 0, '0', '2019-04-21 22:45:08', '2019-05-05 20:20:31', '0', 1);
INSERT INTO `sys_menu` VALUES (2, '用户管理', '', 'user', 'admin/user', 1, 'user', 1, '1', '2019-04-21 22:49:22', '2019-05-12 19:02:34', '0', 1);
INSERT INTO `sys_menu` VALUES (3, '部门管理', '', 'dept', 'admin/dept', 1, 'dept', 2, '1', '2019-04-21 22:52:11', '2019-05-12 21:25:14', '0', 1);
INSERT INTO `sys_menu` VALUES (5, '用户新增', 'sys:user:add', '', NULL, 2, '', 0, '2', '2019-04-22 13:09:11', '2019-06-08 11:21:07', '0', 1);
INSERT INTO `sys_menu` VALUES (6, '系统管理', '', 'sys', '', 0, 'sys', 1, '0', '2019-04-22 23:48:02', '2019-05-06 22:44:51', '0', 1);
INSERT INTO `sys_menu` VALUES (7, '操作日志', '', 'log', 'sys/log', 6, 'log', 1, '1', '2019-04-22 23:59:40', '2019-09-23 15:58:22', '0', 1);
INSERT INTO `sys_menu` VALUES (11, '部门新增', 'sys:dept:add', '', NULL, 3, '', 0, '2', '2019-04-25 11:09:50', '2019-06-08 13:13:45', '0', 1);
INSERT INTO `sys_menu` VALUES (13, '角色管理', '', 'role', 'admin/role', 1, 'peoples', 1, '1', '2019-04-29 21:08:28', '2019-05-05 20:20:53', '0', 1);
INSERT INTO `sys_menu` VALUES (14, '用户修改', 'sys:user:update', '', NULL, 2, '', 0, '2', '2019-04-30 23:43:31', '2019-06-08 11:22:23', '0', 1);
INSERT INTO `sys_menu` VALUES (15, '角色新增', 'sys:role:add', '', NULL, 13, '', 0, '2', '2019-05-01 08:49:21', '2019-06-09 16:39:48', '0', 1);
INSERT INTO `sys_menu` VALUES (16, '菜单管理', '', 'menu', 'admin/menu', 1, 'menu', 3, '1', '2019-05-03 15:26:58', '2019-05-05 20:20:56', '0', 1);
INSERT INTO `sys_menu` VALUES (27, '日志删除', 'sys:log:delete', '', '', 7, '', 0, '2', '2019-05-06 22:47:47', '2019-06-08 13:15:05', '0', 1);
INSERT INTO `sys_menu` VALUES (28, '菜单增加', 'sys:menu:add', '', '', 16, '', 0, '2', '2019-05-08 16:09:43', '2019-06-08 13:14:02', '0', 1);
INSERT INTO `sys_menu` VALUES (29, '菜单修改', 'sys:menu:update', '', '', 16, '', 0, '2', '2019-05-08 16:10:06', '2019-06-08 13:14:05', '0', 1);
INSERT INTO `sys_menu` VALUES (30, '部门修改', 'sys:dept:update', '', '', 3, '', 0, '2', '2019-05-08 23:49:54', '2019-06-08 13:13:49', '0', 1);
INSERT INTO `sys_menu` VALUES (31, '部门删除', 'sys:dept:delete', '', '', 3, '', 0, '2', '2019-05-08 23:53:41', '2019-06-08 13:13:52', '0', 1);
INSERT INTO `sys_menu` VALUES (33, '用户查看', 'sys:user:view', '', '', 2, '', 0, '2', '2019-05-12 18:59:46', '2019-06-08 11:23:01', '0', 1);
INSERT INTO `sys_menu` VALUES (34, '角色修改', 'sys:role:update', '', '', 13, '', 0, '2', '2019-05-12 19:05:03', '2019-06-08 13:13:29', '0', 1);
INSERT INTO `sys_menu` VALUES (35, '用户删除', 'sys:user:delete', '', '', 2, '', 0, '2', '2019-05-12 19:08:13', '2019-06-08 11:23:07', '0', 1);
INSERT INTO `sys_menu` VALUES (36, '菜单删除', 'sys:menu:delete', '', '', 16, '', 0, '2', '2019-05-12 19:10:02', '2019-06-08 13:14:09', '0', 1);
INSERT INTO `sys_menu` VALUES (37, '角色删除', 'sys:role:delete', '', '', 13, '', 0, '2', '2019-05-12 19:11:14', '2019-06-08 13:13:34', '0', 1);
INSERT INTO `sys_menu` VALUES (38, '角色查看', 'sys:role:view', '', '', 13, '', 0, '2', '2019-05-12 19:11:37', '2019-06-08 13:13:37', '0', 1);
INSERT INTO `sys_menu` VALUES (43, '数据字典', '', 'dict', 'sys/dict', 6, 'tag', 0, '1', '2019-05-16 18:17:32', '2019-12-16 15:30:37', '0', 1);
INSERT INTO `sys_menu` VALUES (44, '部门查看', 'sys:dept:view', '', '', 3, '', 0, '2', '2019-06-07 20:50:31', '2019-06-08 13:13:55', '0', 1);
INSERT INTO `sys_menu` VALUES (45, '字典查看', 'sys:dipt:view', '', '', 43, '', 0, '2', '2019-06-07 20:55:42', '2019-06-08 13:14:56', '0', 1);
INSERT INTO `sys_menu` VALUES (46, '菜单查看', 'sys:menu:view', '', '', 16, '', 0, '2', '2019-06-08 13:14:32', NULL, '0', 1);
INSERT INTO `sys_menu` VALUES (47, '修改密码', 'sys:user:updatePass', '', '', 2, '', 0, '2', '2019-06-15 09:43:20', '2019-06-15 09:43:20', '0', 1);
INSERT INTO `sys_menu` VALUES (48, '修改邮箱', 'sys:user:updateEmail', '', '', 2, '', 0, '2', '2019-06-15 09:43:58', '2019-06-15 09:43:58', '0', 1);
INSERT INTO `sys_menu` VALUES (51, '社交账号管理', '', 'social', 'admin/social', 1, 'peoples', 6, '1', '2019-07-19 13:22:44', '2019-07-19 13:24:45', '0', 1);
INSERT INTO `sys_menu` VALUES (52, '解绑账号', 'sys:social:untied', '', '', 51, '', 0, '2', '2019-07-22 13:06:53', '2019-07-22 13:06:53', '0', 1);
INSERT INTO `sys_menu` VALUES (53, '代码生成', '', '/codegen', 'sys/codegen', 6, 'clipboard', 0, '1', '2019-07-25 12:55:37', '2019-08-02 14:52:04', '0', 1);
INSERT INTO `sys_menu` VALUES (54, '社交查看', 'sys:social:view', '', '', 51, '', 0, '2', '2019-08-03 16:16:46', '2019-08-03 16:16:46', '0', 1);
INSERT INTO `sys_menu` VALUES (55, '代码生成', 'sys:codegen:codegen', '', '', 53, '', 0, '2', '2019-08-10 00:08:20', '2019-08-10 00:08:20', '0', 1);
INSERT INTO `sys_menu` VALUES (56, '租户管理', '', 'tenant', 'admin/tenant', 1, 'guide', 5, '1', '2019-08-10 10:52:26', '2019-08-10 10:54:11', '0', 1);
INSERT INTO `sys_menu` VALUES (57, '流程管理', '', 'activiti', '', 0, 'documentation', 2, '0', '2019-10-08 11:03:22', '2019-10-08 11:03:22', '0', 1);
INSERT INTO `sys_menu` VALUES (58, '模型管理', '', 'model', 'activiti/model', 57, 'chart', 0, '1', '2019-10-08 11:06:51', '2019-10-08 11:06:51', '0', 1);
INSERT INTO `sys_menu` VALUES (59, '流程部署', '', 'processDeployment', 'activiti/processDeployment', 57, 'blog', 0, '1', '2019-10-08 13:48:20', '2019-10-08 13:49:02', '0', 1);
INSERT INTO `sys_menu` VALUES (61, '我的流程', '', 'process', '', 0, 'excel', 0, '0', '2019-10-11 10:39:00', '2019-10-11 10:39:00', '0', 1);
INSERT INTO `sys_menu` VALUES (62, '发起流程', '', 'processList', 'process/processList', 61, 'documentation', 0, '1', '2019-10-11 10:49:11', '2019-10-11 10:49:11', '0', 1);
INSERT INTO `sys_menu` VALUES (63, '待签任务', '', 'pendingTask', 'process/pendingTask', 61, 'excel', 0, '1', '2019-10-11 10:54:12', '2019-10-11 10:54:12', '0', 1);
INSERT INTO `sys_menu` VALUES (64, '待办任务', '', 'upcomingTask', 'process/upcomingTask', 61, 'eye-open', 0, '1', '2019-10-11 10:55:46', '2019-10-11 10:55:46', '0', 1);
INSERT INTO `sys_menu` VALUES (65, '已发任务', '', 'deliveredTask', 'process/deliveredTask', 61, 'drag', 0, '1', '2019-10-11 10:57:15', '2019-10-11 10:57:15', '0', 1);
INSERT INTO `sys_menu` VALUES (66, '已完任务', '', 'completedTask', 'process/completedTask', 61, 'clipboard', 0, '1', '2019-10-11 10:58:38', '2019-10-11 10:59:19', '0', 1);
INSERT INTO `sys_menu` VALUES (67, '查看日志', 'sys:log:view', '', '', 7, '', 0, '2', '2019-12-10 16:49:05', '2019-12-10 16:49:05', '0', 1);
INSERT INTO `sys_menu` VALUES (68, '添加字典', 'sys:dict:add', '', '', 43, '', 0, '2', '2019-12-15 21:16:09', '2019-12-15 21:16:09', '0', 1);
INSERT INTO `sys_menu` VALUES (69, '添加字典详情', 'sys:dictItem:add', '', '', 43, '', 0, '2', '2019-12-15 22:08:01', '2019-12-15 22:08:01', '0', 1);
INSERT INTO `sys_menu` VALUES (70, '更新字典详情', 'sys:dictItem:edit', '', '', 43, '', 0, '2', '2019-12-16 12:19:53', '2019-12-16 12:19:53', '0', 1);
INSERT INTO `sys_menu` VALUES (71, '更新字典', 'sys:dict:edit', '', '', 43, '', 0, '2', '2019-12-16 13:44:01', '2019-12-16 13:44:01', '0', 1);
INSERT INTO `sys_menu` VALUES (72, '添加租户', 'sys:tenant:add', '', '', 56, '', 0, '2', '2019-12-17 21:29:52', '2019-12-17 21:29:52', '0', 1);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色主键',
  `role_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '角色名称',
  `role_code` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '角色标识',
  `role_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '角色描述',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '0' COMMENT '删除标识（0-正常,1-删除）',
  `tenant_id` int(11) NULL DEFAULT NULL COMMENT '租户id',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '系统角色表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (5, '管理员', 'ADMIN_ROLE', '管理员', '2019-04-22 21:53:38', '2019-12-15 21:17:07', '0', 1);
INSERT INTO `sys_role` VALUES (7, '开发人员', 'DEV_ROLE', '开发人员', '2019-04-24 21:11:28', '2020-03-18 14:51:09', '0', 1);
INSERT INTO `sys_role` VALUES (8, '体验人员', 'LEARN_ROLE', '专门体验系统', '2019-08-03 15:52:36', '2020-03-18 14:51:02', '0', 1);
INSERT INTO `sys_role` VALUES (9, '默认角色', 'ROLE_ADMIN', NULL, '2019-12-17 21:30:31', '2019-12-17 21:30:31', '0', 2);

-- ----------------------------
-- Table structure for sys_role_enterprise
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_enterprise`;
CREATE TABLE `sys_role_enterprise`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `role_id` int(11) NULL DEFAULT NULL COMMENT '角色ID',
  `enterprise_id` bigint(19) NULL DEFAULT NULL COMMENT '企业ID',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `role_id`(`role_id`) USING BTREE,
  INDEX `enterprise_id`(`enterprise_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '角色与企业对应关系' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_enterprise
-- ----------------------------
INSERT INTO `sys_role_enterprise` VALUES (1, 5, 1);
INSERT INTO `sys_role_enterprise` VALUES (2, 5, 2);
INSERT INTO `sys_role_enterprise` VALUES (3, 5, 3);
INSERT INTO `sys_role_enterprise` VALUES (4, 5, 4);

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `menu_id` int(11) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_role_id`(`role_id`) USING BTREE COMMENT '角色Id',
  INDEX `index_menu_id`(`menu_id`) USING BTREE COMMENT '菜单Id'
) ENGINE = InnoDB AUTO_INCREMENT = 3259 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '角色菜单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (3141, 5, 1);
INSERT INTO `sys_role_menu` VALUES (3142, 5, 2);
INSERT INTO `sys_role_menu` VALUES (3143, 5, 5);
INSERT INTO `sys_role_menu` VALUES (3144, 5, 14);
INSERT INTO `sys_role_menu` VALUES (3145, 5, 33);
INSERT INTO `sys_role_menu` VALUES (3146, 5, 35);
INSERT INTO `sys_role_menu` VALUES (3147, 5, 47);
INSERT INTO `sys_role_menu` VALUES (3148, 5, 48);
INSERT INTO `sys_role_menu` VALUES (3149, 5, 13);
INSERT INTO `sys_role_menu` VALUES (3150, 5, 15);
INSERT INTO `sys_role_menu` VALUES (3151, 5, 34);
INSERT INTO `sys_role_menu` VALUES (3152, 5, 37);
INSERT INTO `sys_role_menu` VALUES (3153, 5, 38);
INSERT INTO `sys_role_menu` VALUES (3154, 5, 3);
INSERT INTO `sys_role_menu` VALUES (3155, 5, 11);
INSERT INTO `sys_role_menu` VALUES (3156, 5, 30);
INSERT INTO `sys_role_menu` VALUES (3157, 5, 31);
INSERT INTO `sys_role_menu` VALUES (3158, 5, 44);
INSERT INTO `sys_role_menu` VALUES (3159, 5, 16);
INSERT INTO `sys_role_menu` VALUES (3160, 5, 28);
INSERT INTO `sys_role_menu` VALUES (3161, 5, 29);
INSERT INTO `sys_role_menu` VALUES (3162, 5, 36);
INSERT INTO `sys_role_menu` VALUES (3163, 5, 46);
INSERT INTO `sys_role_menu` VALUES (3164, 5, 56);
INSERT INTO `sys_role_menu` VALUES (3165, 5, 72);
INSERT INTO `sys_role_menu` VALUES (3166, 5, 52);
INSERT INTO `sys_role_menu` VALUES (3167, 5, 54);
INSERT INTO `sys_role_menu` VALUES (3168, 5, 62);
INSERT INTO `sys_role_menu` VALUES (3169, 5, 63);
INSERT INTO `sys_role_menu` VALUES (3170, 5, 64);
INSERT INTO `sys_role_menu` VALUES (3171, 5, 65);
INSERT INTO `sys_role_menu` VALUES (3172, 5, 66);
INSERT INTO `sys_role_menu` VALUES (3173, 5, 6);
INSERT INTO `sys_role_menu` VALUES (3174, 5, 43);
INSERT INTO `sys_role_menu` VALUES (3175, 5, 45);
INSERT INTO `sys_role_menu` VALUES (3176, 5, 68);
INSERT INTO `sys_role_menu` VALUES (3177, 5, 69);
INSERT INTO `sys_role_menu` VALUES (3178, 5, 70);
INSERT INTO `sys_role_menu` VALUES (3179, 5, 71);
INSERT INTO `sys_role_menu` VALUES (3180, 5, 53);
INSERT INTO `sys_role_menu` VALUES (3181, 5, 55);
INSERT INTO `sys_role_menu` VALUES (3182, 5, 7);
INSERT INTO `sys_role_menu` VALUES (3183, 5, 27);
INSERT INTO `sys_role_menu` VALUES (3184, 5, 67);
INSERT INTO `sys_role_menu` VALUES (3185, 5, 58);
INSERT INTO `sys_role_menu` VALUES (3186, 5, 59);
INSERT INTO `sys_role_menu` VALUES (3187, 9, 1);
INSERT INTO `sys_role_menu` VALUES (3188, 9, 2);
INSERT INTO `sys_role_menu` VALUES (3189, 9, 3);
INSERT INTO `sys_role_menu` VALUES (3190, 9, 5);
INSERT INTO `sys_role_menu` VALUES (3191, 9, 6);
INSERT INTO `sys_role_menu` VALUES (3192, 9, 7);
INSERT INTO `sys_role_menu` VALUES (3193, 9, 11);
INSERT INTO `sys_role_menu` VALUES (3194, 9, 13);
INSERT INTO `sys_role_menu` VALUES (3195, 9, 14);
INSERT INTO `sys_role_menu` VALUES (3196, 9, 15);
INSERT INTO `sys_role_menu` VALUES (3197, 9, 16);
INSERT INTO `sys_role_menu` VALUES (3198, 9, 27);
INSERT INTO `sys_role_menu` VALUES (3199, 9, 28);
INSERT INTO `sys_role_menu` VALUES (3200, 9, 29);
INSERT INTO `sys_role_menu` VALUES (3201, 9, 30);
INSERT INTO `sys_role_menu` VALUES (3202, 9, 31);
INSERT INTO `sys_role_menu` VALUES (3203, 9, 33);
INSERT INTO `sys_role_menu` VALUES (3204, 9, 34);
INSERT INTO `sys_role_menu` VALUES (3205, 9, 35);
INSERT INTO `sys_role_menu` VALUES (3206, 9, 36);
INSERT INTO `sys_role_menu` VALUES (3207, 9, 37);
INSERT INTO `sys_role_menu` VALUES (3208, 9, 38);
INSERT INTO `sys_role_menu` VALUES (3209, 9, 43);
INSERT INTO `sys_role_menu` VALUES (3210, 9, 44);
INSERT INTO `sys_role_menu` VALUES (3211, 9, 45);
INSERT INTO `sys_role_menu` VALUES (3212, 9, 46);
INSERT INTO `sys_role_menu` VALUES (3213, 9, 47);
INSERT INTO `sys_role_menu` VALUES (3214, 9, 48);
INSERT INTO `sys_role_menu` VALUES (3215, 9, 51);
INSERT INTO `sys_role_menu` VALUES (3216, 9, 52);
INSERT INTO `sys_role_menu` VALUES (3217, 9, 54);
INSERT INTO `sys_role_menu` VALUES (3218, 9, 57);
INSERT INTO `sys_role_menu` VALUES (3219, 9, 58);
INSERT INTO `sys_role_menu` VALUES (3220, 9, 59);
INSERT INTO `sys_role_menu` VALUES (3221, 9, 61);
INSERT INTO `sys_role_menu` VALUES (3222, 9, 62);
INSERT INTO `sys_role_menu` VALUES (3223, 9, 63);
INSERT INTO `sys_role_menu` VALUES (3224, 9, 64);
INSERT INTO `sys_role_menu` VALUES (3225, 9, 65);
INSERT INTO `sys_role_menu` VALUES (3226, 9, 66);
INSERT INTO `sys_role_menu` VALUES (3227, 9, 67);
INSERT INTO `sys_role_menu` VALUES (3228, 9, 68);
INSERT INTO `sys_role_menu` VALUES (3229, 9, 69);
INSERT INTO `sys_role_menu` VALUES (3230, 9, 70);
INSERT INTO `sys_role_menu` VALUES (3231, 9, 71);
INSERT INTO `sys_role_menu` VALUES (3232, 8, 1);
INSERT INTO `sys_role_menu` VALUES (3233, 8, 2);
INSERT INTO `sys_role_menu` VALUES (3234, 8, 33);
INSERT INTO `sys_role_menu` VALUES (3235, 8, 13);
INSERT INTO `sys_role_menu` VALUES (3236, 8, 38);
INSERT INTO `sys_role_menu` VALUES (3237, 8, 3);
INSERT INTO `sys_role_menu` VALUES (3238, 8, 44);
INSERT INTO `sys_role_menu` VALUES (3239, 8, 16);
INSERT INTO `sys_role_menu` VALUES (3240, 8, 46);
INSERT INTO `sys_role_menu` VALUES (3241, 8, 61);
INSERT INTO `sys_role_menu` VALUES (3242, 8, 6);
INSERT INTO `sys_role_menu` VALUES (3243, 8, 43);
INSERT INTO `sys_role_menu` VALUES (3244, 8, 45);
INSERT INTO `sys_role_menu` VALUES (3245, 8, 7);
INSERT INTO `sys_role_menu` VALUES (3246, 8, 57);
INSERT INTO `sys_role_menu` VALUES (3247, 7, 1);
INSERT INTO `sys_role_menu` VALUES (3248, 7, 2);
INSERT INTO `sys_role_menu` VALUES (3249, 7, 33);
INSERT INTO `sys_role_menu` VALUES (3250, 7, 13);
INSERT INTO `sys_role_menu` VALUES (3251, 7, 38);
INSERT INTO `sys_role_menu` VALUES (3252, 7, 3);
INSERT INTO `sys_role_menu` VALUES (3253, 7, 44);
INSERT INTO `sys_role_menu` VALUES (3254, 7, 16);
INSERT INTO `sys_role_menu` VALUES (3255, 7, 61);
INSERT INTO `sys_role_menu` VALUES (3256, 7, 6);
INSERT INTO `sys_role_menu` VALUES (3257, 7, 7);
INSERT INTO `sys_role_menu` VALUES (3258, 7, 57);

-- ----------------------------
-- Table structure for sys_tenant
-- ----------------------------
DROP TABLE IF EXISTS `sys_tenant`;
CREATE TABLE `sys_tenant`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '租户id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户名称',
  `code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户编号',
  `start_time` timestamp(0) NULL DEFAULT NULL COMMENT '授权开始时间',
  `end_time` timestamp(0) NULL DEFAULT NULL COMMENT '授权结束时间',
  `status` int(11) NOT NULL DEFAULT 0 COMMENT '0正常 9-冻结',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标记',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '租户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_tenant
-- ----------------------------
INSERT INTO `sys_tenant` VALUES (1, '上海某某公司', '1', '2019-08-10 00:00:00', '2020-08-10 00:00:00', 0, '0', '2019-08-10 10:13:12', '2019-08-10 12:44:52');
INSERT INTO `sys_tenant` VALUES (2, '北京分公司', '2', '2019-12-17 21:30:23', '2021-12-02 00:00:00', 0, '0', '2019-12-17 21:30:31', '2019-12-17 21:30:31');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '密码',
  `email` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '手机号',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '头像',
  `enterprise_id` int(11) NULL DEFAULT NULL COMMENT '企业ID',
  `dept_id` int(11) NULL DEFAULT NULL COMMENT '部门ID',
  `job_id` int(11) NULL DEFAULT NULL COMMENT '岗位ID',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `login_time` datetime(0) NULL DEFAULT NULL COMMENT '登录时间',
  `lock_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '0' COMMENT '0-正常，1-锁定',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '0' COMMENT '0-正常，1-删除',
  `tenant_id` int(11) NULL DEFAULT NULL COMMENT '租户ID',
  PRIMARY KEY (`user_id`) USING BTREE,
  INDEX `enterprise_id`(`enterprise_id`) USING BTREE,
  INDEX `job_id`(`job_id`) USING BTREE,
  INDEX `username`(`username`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (4, 'admin', '$2a$10$uvVjwDE7sYtPlI5Gy5v9H.jj9BigZjokirpGeTIZTiZ1uhu3CJasu', 'lihaodongmail@163.com', '17521296869', NULL, 1, 6, 3, '2019-04-23 23:29:51', '2020-07-29 16:03:30', '2020-07-29 16:03:31', '0', '0', 1);
INSERT INTO `sys_user` VALUES (5, 'develop', '$2a$10$fUuz8/5DGZOGX4zsZC./f.fMAu1LcZT8/DGWf5aAVRzqLYjJZEbwC', 'lihaodongmail@163.com', '17521296869', NULL, 1, 6, NULL, '2020-03-18 14:45:30', '2020-06-04 17:50:56', NULL, '0', '0', 1);

-- ----------------------------
-- Table structure for sys_user_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_log`;
CREATE TABLE `sys_user_log`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `login_time` datetime(0) NULL DEFAULT NULL COMMENT '登录时间',
  `location` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录地点',
  `ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'IP地址',
  `os` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作系统',
  `browser` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '浏览器',
  `tenant_id` int(11) NULL DEFAULT NULL COMMENT '租户ID',
  `del_flag` int(2) NULL DEFAULT NULL COMMENT '是否删除  -1：已删除  0：正常',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `t_login_log_login_time`(`login_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 49 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '登录日志表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `role_id`(`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 46 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '用户角色表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (40, 4, 5);
INSERT INTO `sys_user_role` VALUES (43, 5, 8);
INSERT INTO `sys_user_role` VALUES (44, 7, 9);
INSERT INTO `sys_user_role` VALUES (45, 5, 7);

SET FOREIGN_KEY_CHECKS = 1;
