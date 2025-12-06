package main.java.com.goldrental.service;

import main.java.com.goldrental.dto.AuthRequest;
import main.java.com.goldrental.dto.RegisterRequest;
import main.java.com.goldrental.dto.AuthResponse;

public interface AuthService {

    AuthResponse login(AuthRequest request);

    void register(RegisterRequest request);

    boolean validateToken(String token);
}
