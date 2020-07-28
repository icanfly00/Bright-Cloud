package com.tml.gateway.dto;

import com.tml.common.web.dto.CommonDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Description 限流规则查询参数
 * @Author TuMingLong
 * @Date 2020/7/9 15:34
 */
@ApiModel("限流规则查询参数")
@Data
@EqualsAndHashCode(callSuper = false)
public class GatewayBlackListDto extends CommonDto {
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
     * 请求方法，如果为ALL则表示对所有方法生效
     */
    @ApiModelProperty(value = "请求方法")
    private String requestMethod;
    /**
     * 状态，0关闭，1开启
     */
    @ApiModelProperty(value = "状态，0关闭，1开启", example = "1")
    private String status;
}
