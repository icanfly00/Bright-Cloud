package com.tml.server.generator.entity;

import lombok.Data;

/**
 * @description 
 * @author JacksonTu
 * @since 2020-08-10 20:30
 * @version 1.0
 */
@Data
public class Column {
    /**
     * 名称
     */
    private String name;
    /**
     * 是否为主键
     */
    private Boolean isKey;
    /**
     * 类型
     */
    private String type;
    /**
     * 注释
     */
    private String remark;
    /**
     * 属性名称
     */
    private String field;
}
