package main.java.com.goldrental.controller;


import lombok.RequiredArgsConstructor;
import main.java.com.goldrental.dto.AuthRequest;
import main.java.com.goldrental.dto.AuthResponse;
import main.java.com.goldrental.dto.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import main.java.com.goldrental.service.AuthService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.ok("Registration successful");
    }

    @GetMapping("/validate")
    public ResponseEntity<Boolean> validateToken(@RequestParam String token) {
        return ResponseEntity.ok(authService.validateToken(token));
    }
}
