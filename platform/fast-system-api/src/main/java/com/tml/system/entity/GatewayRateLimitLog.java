package com.tml.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @Description 限流日志
 * @Author TuMingLong
 * @Date 2020/5/10 16:11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("gateway_rate_limit_log")
public class GatewayRateLimitLog extends Model<GatewayRateLimitLog> {

    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 被拦截请求IP
     */
    private String ip;
    /**
     * 被拦截请求URI
     */
    private String requestUri;
    /**
     * 被拦截请求方法
     */
    private String requestMethod;
    /**
     * IP对应地址
     */
    private String location;
    /**
     * 拦截时间点
     */
    private LocalDateTime createTime;
}
