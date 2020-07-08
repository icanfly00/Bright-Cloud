package com.tml.system.service.impl;

import com.tml.common.web.service.impl.BaseServiceImpl;
import com.tml.system.entity.SysMenu;
import com.tml.system.mapper.SysMenuMapper;
import com.tml.system.service.ISysMenuService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description 菜单管理 服务类实现
 * @Author TuMingLong
 * @Date 2020/4/5 16:43
 */
@Service
public class SysMenuServiceImpl extends BaseServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {
    @Override
    public List<String> findPermsByUserId(Integer userId) {
        return this.baseMapper.findPermsByUserId(userId);
    }
}
