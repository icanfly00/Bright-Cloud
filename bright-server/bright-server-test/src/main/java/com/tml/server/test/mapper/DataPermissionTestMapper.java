package com.tml.server.test.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tml.common.starter.datasource.annotation.DataPermission;
import com.tml.server.test.entity.DataPermissionTest;


/**
 * @description
 * @author JacksonTu
 * @since 2020-08-10 20:30
 * @version 1.0
 */
@DataPermission(methods = {"selectPage"})
public interface DataPermissionTestMapper extends BaseMapper<DataPermissionTest> {

}
