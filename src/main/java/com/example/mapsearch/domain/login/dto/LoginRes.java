package com.example.mapsearch.domain.login.dto;

import lombok.Getter;

@Getter
public class LoginRes {

    private String token;

    public LoginRes() {
    }

    public LoginRes(String token) {
        this.token = token;
    }

}