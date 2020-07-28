package com.tml.gateway.service;


import com.tml.common.web.service.IBaseService;
import com.tml.common.web.vo.PageVo;
import com.tml.gateway.dto.GatewayRouteLogDto;
import com.tml.gateway.entity.GatewayRouteLog;

/**
 * @Description 网关日志服务类接口
 * @Author TuMingLong
 * @Date 2020/5/10 16:48
 */
public interface IGatewayRouteLogService extends IBaseService<GatewayRouteLog> {

    PageVo<GatewayRouteLog> pageList(GatewayRouteLogDto gatewayRouteLogDto);
}
