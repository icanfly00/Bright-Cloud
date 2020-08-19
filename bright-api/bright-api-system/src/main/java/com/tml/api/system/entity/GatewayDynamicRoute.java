package com.tml.api.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 动态路由配置表 Entity
 *
 * @author JacksonTu
 * @date 2020-08-13 09:47:27
 */
@Data
@TableName("t_gateway_dynamic_route")
public class GatewayDynamicRoute {

    /**
     * 主键Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 路由名称
     */
    @TableField("route_name")
    private String routeName;

    /**
     * 路由Id
     */
    @TableField("route_id")
    private String routeId;

    /**
     * 路由规则转发的uri
     */
    @TableField("route_uri")
    private String routeUri;

    /**
     * 路由的执行顺序
     */
    @TableField("route_order")
    private Integer routeOrder;

    /**
     * 断言字符串集合，字符串结构：[{
     * "args": {
     * "pattern": "/auth/**"
     * },
     * "name": "Path"
     * }]
     */
    @TableField("predicates")
    private String predicates;

    /**
     * 过滤器字符串集合，字符串结构：[{
     * "args": {
     * "name": "authfallback",
     * "fallbackUri": "forward:/fallback/bright-auth"
     * },
     * "name": "Hystrix"
     * }, {
     * "args": {
     * "_genkey_0": "1"
     * },
     * "name": "StripPrefix"
     * }, {
     * "name": "BrightDocGatewayHeaderFilter"
     * }]
     */
    @TableField("filters")
    private String filters;

    /**
     * 状态：0-不可用；1-可用
     */
    @TableField("enable")
    private Integer enable;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField("update_time")
    private Date updateTime;

    /**
     * 创建人ID
     */
    @TableField("create_user_id")
    private Long createUserId;

    /**
     * 修改人ID
     */
    @TableField("update_user_id")
    private Long updateUserId;

}