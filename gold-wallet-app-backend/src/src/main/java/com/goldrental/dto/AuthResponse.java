package com.goldrental.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponse {

    private String token;    // JWT token
    private String tokenType = "Bearer";
    private Long userId;
    private String name;
    private String email;
    private String role;

    public AuthResponse(Object id, Object fullName, Object role, String token) {}

    public AuthResponse(String token, Long userId, String name, String email, String role) {
        this.token = token;
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.role = role;
    }

}
