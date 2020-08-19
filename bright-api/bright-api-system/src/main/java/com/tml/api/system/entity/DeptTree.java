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
public class DeptTree extends TreeNode<SysDept> {

    private Integer orderNum;
}
