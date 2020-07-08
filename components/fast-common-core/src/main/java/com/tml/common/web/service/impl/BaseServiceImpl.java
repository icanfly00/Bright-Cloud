package com.tml.common.web.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tml.common.web.dto.CommonDto;
import com.tml.common.web.mapper.SuperMapper;
import com.tml.common.web.vo.PageVo;

/**
 * @Description 自定义公用服务层实现类
 * @Author TuMingLong
 * @Date 2020/3/31 19:22
 */
public abstract class BaseServiceImpl<M extends SuperMapper<T>, T> extends ServiceImpl<M, T> {

    public PageVo<T> pageList(CommonDto commonDto) {
        Page page = new Page<>(commonDto.getPage(), commonDto.getLimit());
        IPage iPage = this.baseMapper.pageList(page, commonDto);
        return new PageVo<T>(iPage);
    }
}
