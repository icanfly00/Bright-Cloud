package com.tml.system.mapper;

import com.tml.common.web.mapper.SuperMapper;
import com.tml.system.entity.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description 菜单管理 Mapper接口
 * @Author TuMingLong
 * @Date 2020/3/31 17:15
 */
public interface SysMenuMapper extends SuperMapper<SysMenu> {

    /**
     * 根据用户id查询权限
     *
     * @param userId
     * @return
     */
    List<String> findPermsByUserId(@Param("userId") Integer userId);
}
