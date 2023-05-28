package com.example.mapsearch.domain.login.controller;

import com.example.mapsearch.domain.login.LoginService;
import com.example.mapsearch.domain.login.dto.LoginReq;
import com.example.mapsearch.domain.login.dto.LoginRes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {

    private final LoginService loginService;

    @PostMapping
    public ResponseEntity<LoginRes> login(@RequestBody LoginReq loginRequest) {
        final LoginRes login = loginService.login(loginRequest);
        return ResponseEntity.ok(login);
    }

}
