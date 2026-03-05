package com.eventiya.backend.controller;

import com.eventiya.backend.dto.UserProfileResponse;
import com.eventiya.backend.dto.UpdateUserRequest;
import com.eventiya.backend.service.AccountService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> viewProfile() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        UserProfileResponse response = accountService.fetchUserProfile(username);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/update")
    public ResponseEntity<UserProfileResponse> editProfile(@Valid @RequestBody UpdateUserRequest request) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        UserProfileResponse updatedUser = accountService.modifyUserProfile(username, request);

        return ResponseEntity.ok(updatedUser);
    }
}