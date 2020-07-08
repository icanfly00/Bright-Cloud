package com.tml.system.service.impl;

import com.tml.common.web.service.impl.BaseServiceImpl;
import com.tml.system.entity.SysDict;
import com.tml.system.mapper.SysDictMapper;
import com.tml.system.service.ISysDictService;
import org.springframework.stereotype.Service;

/**
 * @Description 字典管理 服务类实现
 * @Author TuMingLong
 * @Date 2020/4/5 21:16
 */
@Service
public class SysDictServiceImpl extends BaseServiceImpl<SysDictMapper, SysDict> implements ISysDictService {
}
