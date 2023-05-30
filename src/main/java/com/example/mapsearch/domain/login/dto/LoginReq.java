package com.example.mapsearch.domain.login.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginReq {

    @NotNull
    private String email;

    @NotNull
    private String password;

    private String refreshToken;

}
