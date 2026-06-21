package com.bank.authservice.service;

import com.bank.authservice.dto.AuthResponse;
import com.bank.authservice.dto.LoginRequest;
import com.bank.authservice.dto.RegisterRequest;
import com.bank.authservice.entity.Role;
import com.bank.authservice.entity.User;
import com.bank.authservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository repository;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder passwordEncoder;

    public String register(RegisterRequest request) {

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(
                        request.getPassword()))
                .role(Role.valueOf(
                        request.getRole().toUpperCase()))
                .build();

        repository.save(user);

        return "User Registered";
    }

    public AuthResponse login(LoginRequest request) {

        User user = repository
                .findByUsername(request.getUsername())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new RuntimeException(
                    "Invalid Credentials");
        }

        String token = jwtService.generateToken(
                user.getUsername(),
                user.getRole().name());

        return new AuthResponse(token);
    }

}
