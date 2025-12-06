package com.goldrental.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt.secret}")
    private String secretKey;

    @Value("${app.jwt.expirationMs}")
    private long jwtExpirationMs;

    // Get username from token
    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    // Validate token
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;

        } catch (SignatureException ex) {
            System.out.println("Invalid JWT Signature");

        } catch (MalformedJwtException ex) {
            System.out.println("Invalid JWT token");

        } catch (ExpiredJwtException ex) {
            System.out.println("Expired JWT token");

        } catch (UnsupportedJwtException ex) {
            System.out.println("Unsupported JWT token");

        } catch (IllegalArgumentException ex) {
            System.out.println("JWT claims string is empty");
        }

        return false;
    }
}
