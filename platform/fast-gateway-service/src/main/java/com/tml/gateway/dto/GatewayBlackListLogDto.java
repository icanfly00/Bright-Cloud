package com.tml.gateway.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.tml.common.web.dto.CommonDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Description 黑名单日志查询参数
 * @Author TuMingLong
 * @Date 2020/7/9 16:20
 */
@ApiModel("黑名单日志查询参数")
@Data
@EqualsAndHashCode(callSuper = false)
public class GatewayBlackListLogDto extends CommonDto {

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

    @ApiModelProperty(value = "开始时间")
    private String createTimeFrom;

    @ApiModelProperty(value = "结束时间")
    private String createTimeTo;
}
