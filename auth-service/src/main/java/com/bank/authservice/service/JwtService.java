package com.bank.authservice.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    private static final String SECRET =
            "mysecretkeymysecretkeymysecretkeymysecretkey";

    private final SecretKey secretKey =
            Keys.hmacShaKeyFor(SECRET.getBytes());

    public String generateToken(String username, String role) {

        return Jwts.builder()
                .subject(username)
                .claim("role",role)
                .issuedAt(new Date())
                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                        + 86400000))
                .signWith(secretKey)
                .compact();
    }

    public String extractUsername(String token) {

        Claims claims =
                Jwts.parser()
                        .verifyWith(secretKey)
                        .build()
                        .parseSignedClaims(token)
                        .getPayload();

        return claims.getSubject();
    }

    public String extractRole(
            String token){

        Claims claims =
                Jwts.parser()
                        .verifyWith(secretKey)
                        .build()
                        .parseSignedClaims(token)
                        .getPayload();

        return claims.get(
                "role",
                String.class);
    }

    public boolean validateToken(
            String token,
            String username) {

        return extractUsername(token)
                .equals(username);
    }
}