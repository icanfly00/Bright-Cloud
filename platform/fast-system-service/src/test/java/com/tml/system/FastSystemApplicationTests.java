package com.tml.system;

import com.tml.common.redis.service.RedisService;
import org.junit.jupiter.api.Test;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class FastSystemApplicationTests {

    @Resource
    private RedisService redisService;

    @Resource
    private RedissonClient redissonClient;

    @Test
    void contextLoads() {
        String redisKey="fast-cloud:test:1";
        redisService.set(redisKey,"1");
        if(redisService.hasKey(redisKey)){
            String s= (String) redisService.get(redisKey);
            System.out.println(s);
        }else {
            System.out.println("key不存在");
        }
    }

    @Test
    void contextLoads2() throws InterruptedException {
        RLock lock=redissonClient.getLock("anyLock");
        lock.lock(10, TimeUnit.SECONDS);
        boolean res=lock.tryLock(100,10,TimeUnit.SECONDS);
        if(res){
            try {
                contextLoads();
            }finally {
                lock.unlock();
            }
        }
    }

}
