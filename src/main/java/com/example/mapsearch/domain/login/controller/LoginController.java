package com.example.mapsearch.domain.login.controller;

import com.example.mapsearch.domain.login.dto.LoginReq;
import com.example.mapsearch.domain.login.dto.Tokens;
import com.example.mapsearch.domain.login.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

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

    // 로그인 (토큰 발급) 헤더에 실어서 보내주는 방식
    @PostMapping("/v1/login")
    public ResponseEntity<Tokens> login(@RequestBody LoginReq req, HttpServletResponse response) {
        final Tokens tokens = loginService.login(req);
        response.setHeader("X-AUTH-TOKEN", tokens.getAccessToken());
        return ResponseEntity.ok(tokens);
    }

    // 로그인 (토큰 발급) 토큰을 바디에 실어서 보내주는 방식
    @PostMapping("/v2/login")
    public Tokens login2(@RequestBody LoginReq req) {
        return loginService.login(req);
    }

}
