package com.tml.server.msg.service;

import com.tml.server.msg.entity.SysNotice;

import com.tml.common.core.entity.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 系统通告表 Service接口
 *
 * @author JacksonTu
 * @date 2020-10-12 15:02:54
 */
public interface ISysNoticeService extends IService<SysNotice> {
    /**
     * 查询（分页）
     *
     * @param request QueryRequest
     * @param sysNotice sysNotice
     * @return IPage<SysNotice>
     */
    IPage<SysNotice> pageSysNotice(QueryRequest request, SysNotice sysNotice);

    /**
     * 查询（所有）
     *
     * @param sysNotice sysNotice
     * @return List<SysNotice>
     */
    List<SysNotice> listSysNotice(SysNotice sysNotice);

    /**
     * 新增
     *
     * @param sysNotice sysNotice
     */
    void saveSysNotice(SysNotice sysNotice);

    /**
     * 修改
     *
     * @param sysNotice sysNotice
     */
    void updateSysNotice(SysNotice sysNotice);

    /**
     * 删除
     *
     * @param ids
     */
    void deleteSysNotice(String[] ids);
}
