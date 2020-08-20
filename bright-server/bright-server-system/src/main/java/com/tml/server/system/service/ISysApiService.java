package com.tml.server.system.service;

import com.tml.server.system.entity.SysApi;

import com.tml.common.core.entity.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 系统API接口 Service接口
 *
 * @author JacksonTu
 * @date 2020-08-20 19:22:24
 */
public interface ISysApiService extends IService<SysApi> {
    /**
     * 查询（分页）
     *
     * @param request QueryRequest
     * @param sysApi sysApi
     * @return IPage<SysApi>
     */
    IPage<SysApi> pageSysApi(QueryRequest request, SysApi sysApi);

    /**
     * 查询（所有）
     *
     * @param sysApi sysApi
     * @return List<SysApi>
     */
    List<SysApi> listSysApi(SysApi sysApi);

    /**
     * 新增
     *
     * @param sysApi sysApi
     */
    void saveSysApi(SysApi sysApi);

    /**
     * 修改
     *
     * @param sysApi sysApi
     */
    void updateSysApi(SysApi sysApi);

    /**
     * 删除
     *
     * @param ids
     */
    void deleteSysApi(String[] ids);

    /**
     * 检查
     *
     * @param apiCode
     * @return
     */
    boolean check(String apiCode);

    SysApi getSysApiByApiCode(String apiCode);
}
