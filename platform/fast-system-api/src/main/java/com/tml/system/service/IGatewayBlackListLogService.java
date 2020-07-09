package com.tml.system.service;

import com.tml.common.web.service.IBaseService;
import com.tml.common.web.vo.PageVo;
import com.tml.system.dto.GatewayBlackListLogDto;
import com.tml.system.entity.GatewayBlackListLog;

/**
 * @Description 黑名单日志服务类接口
 * @Author TuMingLong
 * @Date 2020/5/10 16:48
 */
public interface IGatewayBlackListLogService extends IBaseService<GatewayBlackListLog> {

    PageVo<GatewayBlackListLog> pageList(GatewayBlackListLogDto gatewayBlackListLogDto);
}
