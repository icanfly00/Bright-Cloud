package com.tml.uaa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description com.tml.system.controller
 * @Author TuMingLong
 * @Date 2020/5/19 19:06
 */
@Controller
public class IndexController {

    @RequestMapping({"/", "/welcome"})
    public String welcome() {
        return "welcome";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

}
