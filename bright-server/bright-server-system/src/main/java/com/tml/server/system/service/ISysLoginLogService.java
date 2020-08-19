package com.tml.server.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tml.api.system.entity.SysLoginLog;
import com.tml.api.system.entity.SysUser;
import com.tml.common.core.entity.QueryRequest;

import java.util.List;
import java.util.Map;

/**
 * @author JacksonTu
 * @version 1.0
 * @description 登录日志业务层
 * @since 2020-08-10 20:30
 */
public interface ISysLoginLogService extends IService<SysLoginLog> {

    /**
     * 获取登录日志分页信息
     *
     * @param loginLog 传参
     * @param request  request
     * @return IPage<LoginLog>
     */
    IPage<SysLoginLog> pageLoginLog(SysLoginLog loginLog, QueryRequest request);

    /**
     * 保存登录日志
     *
     * @param loginLog 登录日志
     */
    void saveLoginLog(SysLoginLog loginLog);

    /**
     * 删除登录日志
     *
     * @param ids 日志 id集合
     */
    void deleteLoginLogs(String[] ids);

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

    /**
     * 通过用户名获取用户最近7次登录日志
     *
     * @param username 用户名
     * @return 登录日志集合
     */
    List<SysLoginLog> findUserLastSevenLoginLogs(String username);
}
