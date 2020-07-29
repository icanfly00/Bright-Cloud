package com.tml.gateway.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Description 首页控制器
 * @Author TuMingLong
 * @Date 2020/7/8 13:46
 */
public class IndexController {

    @Value("${tml.doc.enable}")
    private Boolean enable;

    @GetMapping("/")
    public String index() {
        if (enable) {
            return "redirect:doc.html";
        }
        return "index";
    }

}
