package com.tml.gateway.enhance.controller;

import com.tml.common.core.entity.CommonResult;
import com.tml.gateway.enhance.auth.JwtTokenHelper;
import com.tml.gateway.enhance.service.RouteUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequiredArgsConstructor
@RequestMapping("route")
public class RouteLoginController {

    private final JwtTokenHelper tokenHelper;
    private final PasswordEncoder passwordEncoder;
    private final RouteUserService routeUserService;

    @GetMapping("login")
    public Mono<ResponseEntity<CommonResult>> login(String username, String password) {
        String error = "认证失败，用户名或密码错误";
        return routeUserService.findByUsername(username)
                .map(u -> passwordEncoder.matches(password, u.getPassword()) ?
                        ResponseEntity.ok(new CommonResult().data(tokenHelper.generateToken(u))) :
                        new ResponseEntity<>(new CommonResult().message(error), HttpStatus.INTERNAL_SERVER_ERROR))
                .defaultIfEmpty(new ResponseEntity<>(new CommonResult().message(error), HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
