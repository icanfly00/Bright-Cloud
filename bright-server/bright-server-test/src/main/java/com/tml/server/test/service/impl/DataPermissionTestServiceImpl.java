package com.tml.server.test.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tml.common.core.entity.QueryRequest;
import com.tml.server.test.entity.DataPermissionTest;
import com.tml.server.test.mapper.DataPermissionTestMapper;
import com.tml.server.test.service.IDataPermissionTestService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

;

/**
 * @author JacksonTu
 * @version 1.0
 * @description
 * @since 2020-08-10 20:30
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class DataPermissionTestServiceImpl extends ServiceImpl<DataPermissionTestMapper, DataPermissionTest> implements IDataPermissionTestService {

    @Override
    public IPage<DataPermissionTest> findDataPermissionTests(QueryRequest request, DataPermissionTest dataPermissionTest) {
        LambdaQueryWrapper<DataPermissionTest> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(DataPermissionTest::getCreateTime);
        Page<DataPermissionTest> page = new Page<>(request.getPageNum(), request.getPageSize());
        return this.page(page, queryWrapper);
    }
}
