package com.tml.system.dto;

import com.tml.common.web.dto.CommonDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @Description com.tml.system.dto
 * @Author TuMingLong
 * @Date 9:51
 */
@ApiModel("查询参数对象")
@Data
@EqualsAndHashCode(callSuper = false)
public class OauthClientDetailsDto extends CommonDto {

    @ApiModelProperty(value = "客户端ID")
    @NotNull(message = "客户端ID不能为空")
    private String ClientId;
}
