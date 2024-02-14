package com.aditya.loginsignupapi.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class AuthenticationResponse {
    private String jwt;
    public AuthenticationResponse(String jwt) {
        this.jwt = jwt;
    }
}
