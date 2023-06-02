package com.example.mapsearch.domain.login.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Tokens {

    private String accessToken;

    private String refreshToken;

}