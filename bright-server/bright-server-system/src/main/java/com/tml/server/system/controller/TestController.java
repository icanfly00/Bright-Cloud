package com.tml.server.system.controller;

import com.tml.common.core.entity.CommonResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author JacksonTu
 * @version 1.0
 * @description com.tml.server.system.controller
 * @since 2020/10/18 14:55
 */
@Slf4j
@Validated
@RestController
@RequestMapping("task")
@RequiredArgsConstructor
public class TestController {

    @GetMapping("test/{msg}")
    public CommonResult test(@PathVariable("msg") String msg){
        log.info("定时任务：执行结果：{}",msg);
        return new CommonResult().data("定时任务：执行结果："+msg);
    }
}
