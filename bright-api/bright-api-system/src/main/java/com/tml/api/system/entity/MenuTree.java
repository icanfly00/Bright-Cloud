package com.tml.api.system.entity;


import com.tml.common.core.entity.TreeNode;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description
 * @author JacksonTu
 * @since 2020-08-10 20:30
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MenuTree extends TreeNode<SysMenu> {

    private String path;
    private String component;
    private String perms;
    private String icon;
    private String type;
    private Integer orderNum;
}
