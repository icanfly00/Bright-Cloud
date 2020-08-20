package com.tml.server.test.mapper;

import com.tml.common.starter.datasource.annotation.DataPermission;
import com.tml.server.test.entity.OmsItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 商品表 Mapper
 *
 * @author JacksonTu
 * @date 2020-08-20 11:49:45
 */
@DataPermission(methods = {"selectPage"})
public interface OmsItemMapper extends BaseMapper<OmsItem> {

}
