package com.tml.gateway.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @Description 动态路由配置
 * @Author TuMingLong
 * @Date 2020/7/28 14:13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("gateway_dynamic_route")
public class GatewayDynamicRoute extends Model<GatewayDynamicRoute> {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
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
     * 路由断言集合配置json串
     */
    @TableField("predicates")
    private String predicates;
    /**
     * 路由过滤器集合配置json串
     */
    @TableField("filters")
    private String filters;
    /**
     * 状态：0-不可用；1-可用
     */
    @TableField("enable")
    private Boolean enable;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 修改时间
     */
    private LocalDateTime updateTime;
    /**
     * 创建人
     */
    private Integer createUserId;
    /**
     * 修改人
     */
    private Integer updateUserId;


}
