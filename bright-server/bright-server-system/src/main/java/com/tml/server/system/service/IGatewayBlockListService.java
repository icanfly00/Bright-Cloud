package com.tml.server.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tml.api.system.entity.GatewayBlockList;
import com.tml.common.core.entity.QueryRequest;

import java.util.List;

/**
 * 黑名单 Service接口
 *
 * @author JacksonTu
 * @date 2020-08-13 09:46:55
 */
public interface IGatewayBlockListService extends IService<GatewayBlockList> {
    /**
     * 查询（分页）
     *
     * @param request          QueryRequest
     * @param gatewayBlockList gatewayBlackList
     * @return IPage<GatewayBlockList>
     */
    IPage<GatewayBlockList> pageGatewayBlockList(QueryRequest request, GatewayBlockList gatewayBlockList);

    /**
     * 查询（所有）
     *
     * @param gatewayBlockList gatewayBlackList
     * @return List<GatewayBlockList>
     */
    List<GatewayBlockList> listGatewayBlockList(GatewayBlockList gatewayBlockList);

    /**
     * 检查
     *
     * @param ip
     * @param requestUri
     * @param requestMethod
     * @return
     */
    boolean check(String ip, String requestUri, String requestMethod);

    /**
     * 新增
     *
     * @param gatewayBlockList gatewayBlackList
     */
    void saveGatewayBlockList(GatewayBlockList gatewayBlockList);

    /**
     * 修改
     *
     * @param gatewayBlockList gatewayBlackList
     */
    void updateGatewayBlockList(GatewayBlockList gatewayBlockList);

    /**
     * 删除
     *
     * @param ids
     */
    void deleteGatewayBlockList(String[] ids);

    /**
     * 查询
     *
     * @param
     * @return
     */
    List<GatewayBlockList> listGatewayBlockList();

    /**
     * 缓存黑名单
     */
    void cacheGatewayBlockList();
}
