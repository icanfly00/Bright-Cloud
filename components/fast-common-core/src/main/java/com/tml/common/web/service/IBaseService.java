package com.tml.common.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tml.common.web.dto.CommonDto;
import com.tml.common.web.vo.PageVo;

/**
 * @Description 自定义公用服务层
 * @Author TuMingLong
 * @Date 2020/3/31 19:20
 */
public interface IBaseService<T> extends IService<T> {

    /**
     * 自定义分页
     *
     * @param commonDto
     * @return
     */
    PageVo<T> pageList(CommonDto commonDto);
}
