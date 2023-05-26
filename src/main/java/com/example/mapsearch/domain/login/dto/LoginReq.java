package com.example.mapsearch.domain.login.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class LoginReq {

    @NotNull
    private String email;

    @NotNull
    private String password;

}
