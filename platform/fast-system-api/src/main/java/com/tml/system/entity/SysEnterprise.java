package com.tml.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @Description 企业表
 * @Author TuMingLong
 * @Date 2020/6/4 15:40
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_enterprise")
public class SysEnterprise extends Model<SysEnterprise> {

    private Integer id;

    private String enterpriseName;

    private String code;

    private String legalPerson;

    private String industryCode;

    private String address;

    private String telephone;

    private Integer status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Integer createUserId;

    private Integer updateUserId;

    private Integer defFlag;
}
