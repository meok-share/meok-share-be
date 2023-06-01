package com.example.mapsearch.domain.login.controller;

import com.example.mapsearch.domain.login.dto.LoginReq;
import com.example.mapsearch.domain.login.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class LoginController {

    private final LoginService loginService;

    // 회원가입
    @PostMapping("/join")
    public void join(@RequestBody LoginReq req) {
        loginService.join(req);
    }

    // 로그인
    @PostMapping("/login")
    public String login(@RequestBody LoginReq req) {
       return loginService.login(req);
    }

}
