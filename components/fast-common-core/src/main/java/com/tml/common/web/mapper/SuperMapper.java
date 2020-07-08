package com.tml.common.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tml.common.web.dto.CommonDto;
import org.apache.ibatis.annotations.Param;

/**
 * @Description 自定义mapper层
 * @Author TuMingLong
 * @Date 2020/3/31 19:18
 */
public interface SuperMapper<T> extends BaseMapper<T> {
    /**
     * 自定义分页
     *
     * @param page
     * @param commonDto
     * @return
     */
    IPage<T> pageList(Page<T> page, @Param("ew") CommonDto commonDto);
}
