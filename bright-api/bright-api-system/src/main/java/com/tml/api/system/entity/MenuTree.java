package com.tml.api.system.entity;


import com.tml.common.core.entity.TreeNode;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author JacksonTu
 * @version 1.0
 * @description
 * @since 2020-08-10 20:30
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
