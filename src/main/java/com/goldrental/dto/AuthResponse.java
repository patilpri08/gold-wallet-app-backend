package com.goldrental.dto;

import com.goldrental.entity.Rental;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AuthResponse {

    private String token;    // JWT token
    private String tokenType = "Bearer";
    private Long userId;
    private String name;
    private String email;
    private String role;


    public AuthResponse(Object id, Object fullName, String token) {}

    public AuthResponse(String token, Long userId, String name, String email) {
        this.token = token;
        this.userId = userId;
        this.name = name;
        this.email = email;
    }

}
