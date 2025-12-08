package com.goldrental.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponse {

    private String name;
    private String email;
    private String phone;
    private String token;
    private Long userId;
    private String role;

    public AuthResponse(String phone, String name, String email, String token, Long userId,String role) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.token = token;
        this.userId = userId;
        this.role = role;
    }

}
