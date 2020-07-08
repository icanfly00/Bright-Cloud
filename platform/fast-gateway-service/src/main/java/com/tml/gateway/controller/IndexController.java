package com.tml.gateway.controller;

import com.tml.gateway.configuration.ApiProperties;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;

/**
 * @Description 首页控制器
 * @Author TuMingLong
 * @Date 2020/7/8 13:46
 */
public class IndexController {

    @Resource
    private ApiProperties properties;

    @GetMapping("/")
    public String index(){
        if(properties.getApiDebug()){
            return "redirect:doc.html";
        }
        return "index";
    }


}
