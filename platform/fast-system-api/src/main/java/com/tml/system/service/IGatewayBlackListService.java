package com.tml.system.service;

import com.tml.common.web.service.IBaseService;
import com.tml.common.web.vo.PageVo;
import com.tml.system.dto.GatewayBlackListDto;
import com.tml.system.entity.GatewayBlackList;

import java.util.List;

/**
 * @Description 黑名单服务类接口
 * @Author TuMingLong
 * @Date 2020/5/10 16:48
 */
public interface IGatewayBlackListService extends IBaseService<GatewayBlackList> {

    PageVo<GatewayBlackList> pageList(GatewayBlackListDto gatewayBlackListDto);

    GatewayBlackList findByCondition(GatewayBlackListDto gatewayBlackListDto);

    boolean saveGatewayBlackList(GatewayBlackList gatewayBlackList);

    boolean updateGatewayBlackList(GatewayBlackList gatewayBlackList);

    boolean deleteGatewayBlackList(List<String> ids);

    /**
     * 获取所有状态为开启（1）的黑名单
     *
     * @return
     */
    List<GatewayBlackList> findAllBackList();

}
