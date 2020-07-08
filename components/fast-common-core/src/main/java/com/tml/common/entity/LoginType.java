package com.tml.common.entity;

import lombok.Getter;

/**
 * @Description 登录类型 现在有用户名 短信 社交
 * @Author TuMingLong
 * @Date 2020/5/19 17:40
 */
@Getter
public enum LoginType {
    normal, sms, social;
}
