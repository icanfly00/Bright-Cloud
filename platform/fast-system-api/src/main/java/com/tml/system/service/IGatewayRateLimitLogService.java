package com.tml.system.service;

import com.tml.common.web.service.IBaseService;
import com.tml.common.web.vo.PageVo;
import com.tml.system.dto.GatewayRateLimitLogDto;
import com.tml.system.entity.GatewayRateLimitLog;

/**
 * @Description 限流日志服务类接口
 * @Author TuMingLong
 * @Date 2020/5/10 16:48
 */
public interface IGatewayRateLimitLogService extends IBaseService<GatewayRateLimitLog> {

    PageVo<GatewayRateLimitLog> pageList(GatewayRateLimitLogDto gatewayRateLimitLogDto);

}
