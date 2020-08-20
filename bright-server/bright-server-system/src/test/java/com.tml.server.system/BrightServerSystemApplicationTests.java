package com.tml.server.system;


import cn.hutool.core.codec.Base64;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;


@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BrightServerSystemApplicationTests {

    @Resource
    private PasswordEncoder passwordEncoder;

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
}
