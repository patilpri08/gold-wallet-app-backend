package com.goldrental.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import com.goldrental.entity.User;

import org.springframework.stereotype.Component;

@Component
public class JwtUtils {

    private final String jwtSecret = "mySuperSecretKey"; // use env var or config in real app

    // Generate token from User object
    public String generateToken(User user) {
        // 1 day in milliseconds
        long jwtExpirationMs = 86400000;
        return Jwts.builder()
                .setSubject(user.getName()) // or user.getEmail()
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    // Optional: validate token
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Optional: get username/email from token
    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
