package com.tml.server.test.service.impl;

import com.tml.common.core.entity.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tml.server.test.entity.Eximport;
import com.tml.server.test.mapper.EximportMapper;
import com.tml.server.test.service.IEximportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @description 
 * @author JacksonTu
 * @since 2020-08-10 20:30
 * @version 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class EximportServiceImpl extends ServiceImpl<EximportMapper, Eximport> implements IEximportService {

    @Override
    public IPage<Eximport> findEximports(QueryRequest request, Eximport eximport) {
        Page<Eximport> page = new Page<>(request.getPageNum(), request.getPageSize());
        return this.page(page, null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchInsert(List<Eximport> list) {
        this.saveBatch(list, 1000);
    }

}
