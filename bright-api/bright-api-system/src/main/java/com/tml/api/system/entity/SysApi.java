package com.tml.api.system.entity;

import java.util.Date;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
* 系统API接口 Entity
*
* @author JacksonTu
* @date 2020-08-20 19:22:24
*/
@Data
@TableName("t_sys_api")
public class SysApi {

    /**
     * 接口ID
     */
    @TableId(value = "api_id", type = IdType.AUTO)
    private Long apiId;

    /**
     * 接口编码
     */
    @TableField("api_code")
    private String apiCode;

    /**
     * 接口名称
     */
    @TableField("api_name")
    private String apiName;

    /**
     * 接口分类:default-默认分类
     */
    @TableField("api_category")
    private String apiCategory;

    /**
     * 资源描述
     */
    @TableField("api_desc")
    private String apiDesc;

    /**
     * 请求方式
     */
    @TableField("request_method")
    private String requestMethod;

    /**
     * 响应类型
     */
    @TableField("content_type")
    private String contentType;

    /**
     * 服务ID
     */
    @TableField("service_id")
    private String serviceId;

    /**
     * 请求路径
     */
    @TableField("path")
    private String path;

    /**
     * 优先级
     */
    @TableField("priority")
    private Long priority;

    /**
     * 状态:0-无效 1-有效
     */
    @TableField("status")
    private Integer status;

    /**
     * 保留数据0-否 1-是 不允许删除
     */
    @TableField("is_persist")
    private Integer isPersist;

    /**
     * 是否需要认证: 0-无认证 1-身份认证 默认:1
     */
    @TableField("is_auth")
    private Integer isAuth;

    /**
     * 是否公开: 0-内部的 1-公开的
     */
    @TableField("is_open")
    private Integer isOpen;

    /**
     * 类名
     */
    @TableField("class_name")
    private String className;

    /**
     * 方法名
     */
    @TableField("method_name")
    private String methodName;

    /**
     *
     */
    @TableField("create_time")
    private Date createTime;

    /**
     *
     */
    @TableField("update_time")
    private Date updateTime;

    /**
     * 搜索前缀
     */
    @TableField(exist = false)
    private String prefix;

}