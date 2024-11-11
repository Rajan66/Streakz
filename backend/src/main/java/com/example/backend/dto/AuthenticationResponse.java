package com.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationResponse {
    private UserDto user;
    private String token;
    private Date issuedDate;
    private Date expirationDate;
    private String errorMessage;

    public AuthenticationResponse(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
