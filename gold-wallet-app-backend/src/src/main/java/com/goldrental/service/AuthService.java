package com.goldrental.service;

import com.goldrental.dto.AuthRequest;
import com.goldrental.dto.RegisterRequest;
import com.goldrental.dto.AuthResponse;

public interface AuthService {

    AuthResponse login(AuthRequest request);

    void register(RegisterRequest request);

    boolean validateToken(String token);
}
