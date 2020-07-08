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
 * @Description 黑名单
 * @Author TuMingLong
 * @Date 2020/5/10 16:10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("gateway_back_list")
public class GatewayBlackList extends Model<GatewayBlackList> {

    public static final String CLOSE = "0";
    public static final String OPEN = "1";
    public static final String METHOD_ALL = "all";

    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 黑名单ip
     */
    private String ip;
    /**
     * 请求URI
     */
    private String requestUri;
    /**
     * 请求方法，如果为ALL则表示对所有方法生效
     */
    private String requestMethod;
    /**
     * 限制时间起
     */
    private String limitFrom;
    /**
     * 限制时间止
     */
    private String limitTo;
    /**
     * ip对应地址
     */
    private String location;
    /**
     * 状态，0关闭，1开启
     */
    private String status;
    /**
     * 规则创建时间
     */
    private LocalDateTime createTime;
    /**
     * 规则更新时间
     */
    private LocalDateTime updateTime;
    /**
     * 创建用户
     */
    private Integer createUserId;
    /**
     * 更新用户
     */
    private Integer updateUserId;


}
