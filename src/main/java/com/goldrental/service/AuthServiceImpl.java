package main.java.com.goldrental.service;

import main.java.com.goldrental.dto.AuthRequest;
import main.java.com.goldrental.dto.RegisterRequest;
import main.java.com.goldrental.dto.AuthResponse;
import main.java.com.goldrental.entity.User;
import main.java.com.goldrental.repository.UserRepository;
import main.java.com.goldrental.security.JwtUtils;
import main.java.com.goldrental.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Override
    public AuthResponse login(AuthRequest request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Jeweller not found"));

        String token = jwtUtils.generateToken(user);

        return new AuthResponse(user.getId(), user.getName(), user.getRole(), token);
    }

    @Override
    public void register(RegisterRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        userRepository.save(user);
    }

    @Override
    public boolean validateToken(String token) {
        return jwtUtils.validateToken(token);
    }
}
