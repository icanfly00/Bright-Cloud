package com.tml.api.system.entity;


import java.util.Date;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
* 网关日志 Entity
*
* @author JacksonTu
* @date 2020-08-13 09:47:06
*/
@Data
@TableName("t_gateway_route_log")
public class GatewayRouteLog {

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 请求IP
     */
    @TableField("ip")
    private String ip;

    /**
     * 请求URI
     */
    @TableField("request_uri")
    private String requestUri;

    /**
     * 请求方法
     */
    @TableField("request_method")
    private String requestMethod;

    /**
     * 目标URI
     */
    @TableField("target_uri")
    private String targetUri;

    /**
     * 目标服务
     */
    @TableField("target_server")
    private String targetServer;

    /**
     * IP对应地址
     */
    @TableField("location")
    private String location;

    /**
     * 请求状态
     */
    @TableField("http_status")
    private Integer httpStatus;
    /**
     * 请求参数
     */
    @TableField("params")
    private String params;

    /**
     * 请求头
     */
    @TableField("headers")
    private String headers;

    /**
     * userAgent
     */
    @TableField("user_agent")
    private String userAgent;

    /**
     * 认证信息
     */
    @TableField("authentication")
    private String authentication;

    /**
     * 拦截时间点
     */
    @TableField("create_time")
    private Date createTime;

    @TableField(exist = false)
    private String createTimeFrom;

    @TableField(exist = false)
    private String createTimeTo;

}