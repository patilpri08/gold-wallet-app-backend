package com.goldrental.controller;


import com.goldrental.dto.AuthRequest;
import com.goldrental.dto.AuthResponse;
import com.goldrental.dto.RegisterJweller;
import com.goldrental.dto.RegisterRequest;
import com.goldrental.service.AuthService;
import com.goldrental.service.JewellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final JewellerService jewellerService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.ok("Registration successful");
    }

    @PostMapping("/registerJeweller")
    public ResponseEntity<?> register(@RequestBody RegisterJweller registerJweller) {
        return ResponseEntity.ok(jewellerService.registerJeweller(registerJweller));
    }

    @GetMapping("/validate")
    public ResponseEntity<Boolean> validateToken(@RequestParam String token) {
        return ResponseEntity.ok(authService.validateToken(token));
    }
}
