package com.tml.gateway.dto;

import com.tml.common.web.dto.CommonDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Description 网关日志查询参数
 * @Author TuMingLong
 * @Date 2020/7/9 17:04
 */
@ApiModel("网关日志查询参数")
@Data
@EqualsAndHashCode(callSuper = false)
public class GatewayRouteLogDto extends CommonDto {
    /**
     * 请求IP
     */
    @ApiModelProperty(value = "请求IP")
    private String ip;
    /**
     * 请求URI
     */
    @ApiModelProperty(value = "请求URI")
    private String requestUri;
    /**
     * 请求方法
     */
    @ApiModelProperty(value = "请求方法")
    private String requestMethod;
    /**
     * 目标服务
     */
    @ApiModelProperty(value = "目标服务")
    private String targetServer;

    @ApiModelProperty(value = "开始时间")
    private String createTimeFrom;

    @ApiModelProperty(value = "结束时间")
    private String createTimeTo;
}
