package com.tml.gateway.service;


import com.tml.common.web.service.IBaseService;
import com.tml.common.web.vo.PageVo;
import com.tml.gateway.dto.GatewayRouteLimitLogDto;
import com.tml.gateway.entity.GatewayRouteLimitLog;

/**
 * @Description 限流日志 服务类接口
 * @Author TuMingLong
 * @Date 2020/5/10 16:48
 */
public interface IGatewayRouteLimitLogService extends IBaseService<GatewayRouteLimitLog> {

    PageVo<GatewayRouteLimitLog> pageList(GatewayRouteLimitLogDto gatewayRouteLimitLogDto);

}
