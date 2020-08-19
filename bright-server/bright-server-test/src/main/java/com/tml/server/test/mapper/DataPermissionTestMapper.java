package com.tml.server.test.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tml.common.starter.datasource.annotation.DataPermission;
import com.tml.server.test.entity.DataPermissionTest;


/**
 * @author JacksonTu
 * @version 1.0
 * @description
 * @since 2020-08-10 20:30
 */
@DataPermission(methods = {"selectPage"})
public interface DataPermissionTestMapper extends BaseMapper<DataPermissionTest> {

}
