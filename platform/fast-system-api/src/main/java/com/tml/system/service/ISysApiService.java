package com.tml.system.service;


import com.tml.common.web.service.IBaseService;
import com.tml.system.entity.SysApi;

import java.util.List;

/**
 * 接口资源管理
 *
 * @author liuyadu
 */
public interface ISysApiService extends IBaseService<SysApi> {
   

    /**
     * 查询列表
     *
     * @return
     */
    List<SysApi> findList(String serviceId);

    /**
     * 根据主键获取接口
     *
     * @param apiId
     * @return
     */
    SysApi getApi(Long apiId);


    /**
     * 检查接口编码是否存在
     *
     * @param apiCode
     * @return
     */
    Boolean isExist(String apiCode);

    /**
     * 添加接口
     *
     * @param api
     * @return
     */
    void addApi(SysApi api);

    /**
     * 修改接口
     *
     * @param api
     * @return
     */
    void updateApi(SysApi api);

    /**
     * 查询接口
     *
     * @param apiCode
     * @return
     */
    SysApi getApi(String apiCode);

    /**
     * 移除接口
     *
     * @param apiId
     * @return
     */
    void removeApi(Long apiId);

}
