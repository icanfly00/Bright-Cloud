package com.tml.gateway.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @Description 网关日志
 * @Author TuMingLong
 * @Date 2020/5/10 16:12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("gateway_route_log")
public class GatewayRouteLog extends Model<GatewayRouteLog> {

    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 请求IP
     */
    private String ip;
    /**
     * 请求URI
     */
    private String requestUri;
    /**
     * 目标URI
     */
    private String targetUri;
    /**
     * 请求方法
     */
    private String requestMethod;
    /**
     * 目标服务
     */
    private String targetServer;
    /**
     * 请求地点
     */
    private String location;
    /**
     * 响应状态
     */
    private int httpStatus;
    /**
     * 请求时间
     */
    private LocalDateTime requestTime;
    /**
     * 响应时间
     */
    private LocalDateTime responseTime;
    /**
     * 消耗时间
     */
    private Long consumingTime;
    /**
     * 请求数据
     */
    private String params;
    /**
     * 请求头
     */
    private String headers;
    /**
     * userAgent
     */
    private String userAgent;
    /**
     * 认证用户信息
     */
    private String authentication;
    /**
     * 错误信息
     */
    private String error;
    /**
     * 请求时间点
     */
    private LocalDateTime createTime;
}
