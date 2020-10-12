package com.tml.server.msg.service;

import com.tml.server.msg.entity.SysNoticeSend;

import com.tml.common.core.entity.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 用户通告阅读标记表 Service接口
 *
 * @author JacksonTu
 * @date 2020-10-12 15:03:04
 */
public interface ISysNoticeSendService extends IService<SysNoticeSend> {
    /**
     * 查询（分页）
     *
     * @param request QueryRequest
     * @param sysNoticeSend sysNoticeSend
     * @return IPage<SysNoticeSend>
     */
    IPage<SysNoticeSend> pageSysNoticeSend(QueryRequest request, SysNoticeSend sysNoticeSend);

    /**
     * 查询（所有）
     *
     * @param sysNoticeSend sysNoticeSend
     * @return List<SysNoticeSend>
     */
    List<SysNoticeSend> listSysNoticeSend(SysNoticeSend sysNoticeSend);

    /**
     * 新增
     *
     * @param sysNoticeSend sysNoticeSend
     */
    void saveSysNoticeSend(SysNoticeSend sysNoticeSend);

    /**
     * 修改
     *
     * @param sysNoticeSend sysNoticeSend
     */
    void updateSysNoticeSend(SysNoticeSend sysNoticeSend);

    /**
     * 删除
     *
     * @param ids
     */
    void deleteSysNoticeSend(String[] ids);
}
