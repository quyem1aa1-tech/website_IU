package com.app.controller;

import com.app.dto.AuthResponse;
import com.app.dto.LoginRequest;
import com.app.dto.SignupRequest;
import com.app.service.AuthService;
import com.app.entity.LoginStatus;
import com.app.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService service;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        LoginStatus status = service.login(request.getUsername(), request.getPassword());

        return switch (status) {
            case SUCCESS -> {
                // Take old information from user to return
                User user = service.getUserByUsername(request.getUsername()).get();
                yield ResponseEntity.ok(new AuthResponse("success", "Welcome!", user.getUsername(), user.getRole()));
            }

            case USER_NOT_FOUND -> ResponseEntity.status(404).body("Error: Username does not exist.");

            case WRONG_PASSWORD -> ResponseEntity.status(401).body("Error: Incorrect password. Please try again.");

            default -> ResponseEntity.status(500).body("An unknown error occurred.");
        };
    }

    @PostMapping("/signup")
    public String signup(@RequestBody SignupRequest request) {
        AuthService service = new AuthService();
        return service.registerUser(request);
    }
}