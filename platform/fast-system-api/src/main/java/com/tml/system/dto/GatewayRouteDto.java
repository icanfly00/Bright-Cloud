package com.tml.system.dto;

import com.tml.common.web.dto.CommonDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Description 动态网关查询参数
 * @Author TuMingLong
 * @Date 2020/7/9 17:15
 */
@ApiModel("动态网关查询参数")
@Data
@EqualsAndHashCode(callSuper = false)
public class GatewayRouteDto extends CommonDto {
    /**
     * 服务ID
     */
    @ApiModelProperty(value = "服务ID")
    private String serviceId;
    /**
     * 状态，0关闭，1开启
     */
    @ApiModelProperty(value = "状态，0关闭，1开启", example = "1")
    private Integer status;
}
