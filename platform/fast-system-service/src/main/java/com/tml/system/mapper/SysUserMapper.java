package com.tml.system.mapper;

import com.tml.common.web.mapper.SuperMapper;
import com.tml.system.entity.SysUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import java.util.List;


/**
 * @Description com.tml.system
 * @Author TuMingLong
 * @Date 2020/3/31 17:15
 */
@Mapper
public interface SysUserMapper extends SuperMapper<SysUser> {

    @Insert("insert into sys_user (username,password,dept_id,job_id,phone,email,avatar,lock_flag) values (#{username},#{password},#{deptId},#{jobId},#{phone},#{email},#{avatar},#{lockFlag})")
    @Options(useGeneratedKeys = true, keyProperty = "userId", keyColumn = "user_id")
    boolean insertUser(SysUser sysUser);

    /**
     * 通过用户ID查询数据权限
     *
     * @param userId
     * @return
     */
    List<Integer> findDataPermsByUserId(Integer userId);

}
