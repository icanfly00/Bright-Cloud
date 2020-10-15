package com.tml.server.msg.mapper;

import com.tml.server.msg.entity.SysNoticeSend;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户通告阅读标记表 Mapper
 *
 * @author JacksonTu
 * @date 2020-10-12 15:03:04
 */
public interface SysNoticeSendMapper extends BaseMapper<SysNoticeSend> {

    List<Long> listByUserId(@Param("userId") Long userId);

}
