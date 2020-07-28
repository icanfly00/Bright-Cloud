package com.tml.gateway.dto;

import com.tml.common.web.dto.CommonDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Description 动态路由配置查询参数
 * @Author TuMingLong
 * @Date 2020/7/28 15:51
 */
@ApiModel("动态路由配置查询参数")
@Data
@EqualsAndHashCode(callSuper = false)
public class GatewayDynamicRouteDto extends CommonDto {

    /**
     * 路由名称
     */
    @ApiModelProperty(value = "路由名称")
    private String routeName;

    /**
     * 路由ID
     */
    @ApiModelProperty(value = "路由ID")
    private String routeId;

}
