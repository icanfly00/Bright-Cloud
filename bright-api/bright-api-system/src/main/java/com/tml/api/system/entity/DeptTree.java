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
public class DeptTree extends TreeNode<SysDept> {

    private Integer orderNum;
}
