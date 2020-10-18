package com.tml.server.system;


import cn.hutool.core.codec.Base64;
import com.tml.api.system.entity.SysApi;
import com.tml.server.system.service.ISysApiService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Map;


@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BrightServerSystemApplicationTests {

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private ISysApiService apiService;

    @Test
    public void contextLoads() {
        String s=passwordEncoder.encode("123456");
        System.out.println("客户端加密后密码："+s);
        String a = "bright:123456";
        String encode = Base64.encode(a);
        String decodeStr = Base64.decodeStr(encode);
        System.out.println("Base64编码："+encode);
        System.out.println("Base64解码："+decodeStr);
    }

    @Test
    public void testTreeApi() {
        SysApi api=new SysApi();
        api.setPrefix("/task");
        Map<String,Object> params=apiService.treeApi(api);
        // 5. 使用 Stream API 遍历 HashMap
        params.entrySet().stream().forEach((entry) -> {
                System.out.println(entry.getKey()+":"+entry.getValue());
        });
    }
}
