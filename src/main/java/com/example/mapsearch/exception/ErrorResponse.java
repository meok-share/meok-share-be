package com.example.mapsearch.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ErrorResponse {
    private String code;
    private String message;
    private List<Error> errors;

    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ErrorResponse(String code, String message, List<Error> errors) {
        this.code = code;
        this.message = message;
        this.errors = errors;
    }

}