package com.tml.gateway.entity;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.tml.common.util.JacksonUtil;
import com.tml.gateway.dto.GatewayFilterDefinition;
import com.tml.gateway.dto.GatewayPredicateDefinition;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

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
    private Integer enable;
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

    /**
     * 获取断言集合
     * @return
     */
    public List<GatewayPredicateDefinition> getPredicateDefinitionList(){
        if(StringUtils.isNoneBlank(this.predicates)){
            return JSON.parseArray(this.predicates,GatewayPredicateDefinition.class);
        }
        return null;
    }

    /**
     * 获取过滤器集合
     * @return
     */
    public List<GatewayFilterDefinition> getFilterDefinitionList(){
        if(StringUtils.isNoneBlank(this.filters)){
            return JSON.parseArray(this.filters,GatewayFilterDefinition.class);
        }
        return null;
    }
}
