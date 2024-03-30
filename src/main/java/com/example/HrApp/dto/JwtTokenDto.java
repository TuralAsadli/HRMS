package com.example.HrApp.dto;

import lombok.Data;

@Data
public class JwtTokenDto {
    private String token;
    private String tokenType;

    public JwtTokenDto(String token){
        this.token = token;
    }
}
