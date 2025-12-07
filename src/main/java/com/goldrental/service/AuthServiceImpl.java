package com.goldrental.service;

import com.goldrental.dto.AuthRequest;
import com.goldrental.dto.RegisterRequest;
import com.goldrental.dto.AuthResponse;
import com.goldrental.entity.User;
import com.goldrental.entity.Wallet;
import com.goldrental.repository.UserRepository;
import com.goldrental.repository.WalletRepository;
import com.goldrental.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final WalletRepository walletRepository;

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

        return new AuthResponse(user.getPhone(), user.getName(), user.getEmail(),token,user.getId());
    }

    @Override
    public void register(RegisterRequest request) {
        User savedUser = new User();
        savedUser.setName(request.getName());
        savedUser.setEmail(request.getEmail());
        savedUser.setPhone(request.getPhone());
        savedUser.setPassword(passwordEncoder.encode(request.getPassword()));
        savedUser.setRole(request.getRole());

        userRepository.save(savedUser);

        // Create wallet for the new savedUser
        Wallet wallet = new Wallet();
        wallet.setUser(savedUser);
        wallet.setBalance(BigDecimal.ZERO); // start with 0 balance
        walletRepository.save(wallet);

    }

    @Override
    public boolean validateToken(String token) {
        return jwtUtils.validateToken(token);
    }
}
