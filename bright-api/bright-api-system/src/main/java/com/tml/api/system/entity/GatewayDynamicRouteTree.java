package com.tml.api.system.entity;

import com.tml.common.core.entity.TreeNode;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author JacksonTu
 * @version 1.0
 * @description 动态路由树
 * @since 2020/10/18 14:39
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GatewayDynamicRouteTree extends TreeNode<GatewayDynamicRoute> {
}
