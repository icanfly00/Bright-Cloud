package com.tml.server.msg.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tml.server.msg.entity.SysNotice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统通告表 Mapper
 *
 * @author JacksonTu
 * @date 2020-10-12 15:02:54
 */
public interface SysNoticeMapper extends BaseMapper<SysNotice> {

    /**
     * 未读消息
     * @param page
     * @param userId
     * @param msgCategory
     * @return
     */
   List<SysNotice> pageUnreadMsg(Page<SysNotice> page, @Param("userId")Long userId, @Param("msgCategory")String msgCategory);

}
