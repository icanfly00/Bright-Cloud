package com.tml.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Description 首页控制器
 * @Author TuMingLong
 * @Date 2020/7/8 13:46
 */
public class IndexController {

    @GetMapping("/")
    public String index() {
            return "redirect:doc.html";
    }

}
