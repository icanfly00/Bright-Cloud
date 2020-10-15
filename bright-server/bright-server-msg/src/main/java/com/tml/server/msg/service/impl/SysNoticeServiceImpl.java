package com.tml.server.msg.service.impl;

import com.tml.server.msg.entity.SysNotice;
import com.tml.server.msg.mapper.SysNoticeMapper;
import com.tml.server.msg.service.ISysNoticeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import lombok.RequiredArgsConstructor;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tml.common.core.entity.QueryRequest;
import java.util.Arrays;
import java.util.List;

/**
 * 系统通告表 Service实现
 *
 * @author JacksonTu
 * @date 2020-10-12 15:02:54
 */
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class SysNoticeServiceImpl extends ServiceImpl<SysNoticeMapper, SysNotice> implements ISysNoticeService {

    @Override
    public IPage<SysNotice> pageSysNotice(QueryRequest request, SysNotice sysNotice) {
        LambdaQueryWrapper<SysNotice> queryWrapper = new LambdaQueryWrapper<>(sysNotice);
        // TODO 设置查询条件
        Page<SysNotice> page = new Page<>(request.getPageNum(), request.getPageSize());
        return this.page(page, queryWrapper);
    }

    @Override
    public List<SysNotice> listSysNotice(SysNotice sysNotice) {
        LambdaQueryWrapper<SysNotice> queryWrapper = new LambdaQueryWrapper<>();
        // TODO 设置查询条件
        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveSysNotice(SysNotice sysNotice) {
        this.save(sysNotice);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSysNotice(SysNotice sysNotice) {
        this.saveOrUpdate(sysNotice);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteSysNotice(String[] ids) {
        List<String> list = Arrays.asList(ids);
        this.removeByIds(list);
    }
}