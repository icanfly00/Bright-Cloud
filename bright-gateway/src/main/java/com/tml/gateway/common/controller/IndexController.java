package com.tml.gateway.common.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @description
 * @author JacksonTu
 * @since 2020-08-10 20:30
 * @version 1.0
 */
@RestController
public class IndexController {

    @RequestMapping("/")
    public Mono<String> index() {
        return Mono.just("bright cloud gateway");
    }
}
