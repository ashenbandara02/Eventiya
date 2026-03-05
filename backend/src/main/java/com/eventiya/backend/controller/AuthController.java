package com.eventiya.backend.controller;

import com.eventiya.backend.dto.AuthResponse;
import com.eventiya.backend.dto.LoginRequest;
import com.eventiya.backend.dto.RegisterRequest;
import com.eventiya.backend.service.AuthService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserAuthController {

    private final AuthService authService;

    @Autowired
    public UserAuthController(AuthService authService) {
        this.authService = authService;
    }

    // Register endpoint
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody RegisterRequest registerRequest) {

        authService.register(registerRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("User account created successfully");
    }

    // Login endpoint
    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signin(@Valid @RequestBody LoginRequest loginRequest) {

        AuthResponse authResponse = authService.authenticate(loginRequest);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authResponse);
    }
}
