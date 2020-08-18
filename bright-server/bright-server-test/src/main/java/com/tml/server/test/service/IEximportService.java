package com.tml.server.test.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tml.common.core.entity.QueryRequest;
import com.tml.server.test.entity.Eximport;

import java.util.List;


/**
 * @description 
 * @author JacksonTu
 * @since 2020-08-10 20:30
 * @version 1.0
 */
public interface IEximportService extends IService<Eximport> {
    /**
     * 查询（分页）
     *
     * @param request  QueryRequest
     * @param eximport eximport
     * @return IPage<Eximport>
     */
    IPage<Eximport> findEximports(QueryRequest request, Eximport eximport);


    /**
     * 批量插入
     *
     * @param list List<Eximport>
     */
    void batchInsert(List<Eximport> list);

}
