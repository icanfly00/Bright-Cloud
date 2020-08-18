package com.tml.server.test.service;

import com.tml.common.core.entity.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tml.server.test.entity.DataPermissionTest;


/**
 * @description
 * @author JacksonTu
 * @since 2020-08-10 20:30
 * @version 1.0
 */
public interface IDataPermissionTestService extends IService<DataPermissionTest> {
    /**
     * 查询（分页）
     *
     * @param request            QueryRequest
     * @param dataPermissionTest dataPermissionTest
     * @return IPage<DataPermissionTest>
     */
    IPage<DataPermissionTest> findDataPermissionTests(QueryRequest request, DataPermissionTest dataPermissionTest);
}
