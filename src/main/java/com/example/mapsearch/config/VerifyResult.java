package com.example.mapsearch.config;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VerifyResult {

    private boolean success;

    private String userEmail;

    public VerifyResult(final boolean success, final String userEmail) {
        this.success = success;
        this.userEmail = userEmail;
    }

    public boolean isSuccess() {
        return this.success;
    }

}
