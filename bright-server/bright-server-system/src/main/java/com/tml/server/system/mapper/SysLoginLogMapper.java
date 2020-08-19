package com.tml.server.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tml.api.system.entity.SysLoginLog;
import com.tml.api.system.entity.SysUser;

import java.util.List;
import java.util.Map;

/**
 * @author JacksonTu
 * @version 1.0
 * @description 登录日志Mapper接口
 * @since 2020-08-10 20:30
 */
public interface SysLoginLogMapper extends BaseMapper<SysLoginLog> {

    /**
     * 获取系统总访问次数
     *
     * @return Long
     */
    Long findTotalVisitCount();

    /**
     * 获取系统今日访问次数
     *
     * @return Long
     */
    Long findTodayVisitCount();

    /**
     * 获取系统今日访问 IP数
     *
     * @return Long
     */
    Long findTodayIp();

    /**
     * 获取系统近十天来的访问记录
     *
     * @param user 用户
     * @return 系统近十天来的访问记录
     */
    List<Map<String, Object>> findLastTenDaysVisitCount(SysUser user);

}