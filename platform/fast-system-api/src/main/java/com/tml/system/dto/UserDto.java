package com.tml.system.dto;


import com.tml.common.web.dto.CommonDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


/**
 * @Description 用户Dto
 * @Author TuMingLong
 * @Date 2020/3/31 11:48
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserDto extends CommonDto {

    private Integer userId;
    private String username;
    private String password;
    private Integer deptId;
    private String phone;
    private String email;
    private String avatar;
    private String lockFlag;
    private String delFlag;
    private List<Integer> roleList;
    private List<Integer> deptList;
    /**
     * 新密码
     */
    private String newPassword;
    private String smsCode;
}
