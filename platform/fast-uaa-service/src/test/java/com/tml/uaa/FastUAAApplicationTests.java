package com.tml.uaa;


import com.tml.common.api.CommonResult;
import com.tml.system.entity.SysUser;
import com.tml.uaa.service.feign.SysUserFeignService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Set;


@SpringBootTest
class FastUAAApplicationTests {

    @Resource
    private SysUserFeignService sysUserFeignService;

    @Test
    void contextLoads() {
       CommonResult<Set<String>> commonResult=sysUserFeignService.findPermsByUserId(4);
       if(commonResult.getCode()==200){
           Set<String> perms=commonResult.getData();
           perms.stream().forEach(s -> {
               System.out.println(s);
           });
       }

        CommonResult<SysUser> commonResult2=sysUserFeignService.findSecurityUserByUsername("admin");
        if(commonResult2.getCode()==200){
            SysUser user=commonResult2.getData();
            System.out.println(user);
        }
    }

}
