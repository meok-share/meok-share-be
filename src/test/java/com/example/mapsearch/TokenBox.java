package com.example.mapsearch;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenBox {

    private String authToken;

    private String refreshToken;

}
