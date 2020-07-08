package com.tml.system.dto;

import com.tml.common.web.dto.CommonDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Description com.tml.system.dto
 * @Author TuMingLong
 * @Date 9:51
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OauthClientDetailsDto extends CommonDto {

    private String ClientId;
}
