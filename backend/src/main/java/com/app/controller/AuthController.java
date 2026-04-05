package com.app.controller;

import com.app.dto.AuthResponse;
import com.app.dto.LoginData;
import com.app.service.UserService;
import com.app.entity.LoginStatus;
import com.app.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginData data) {
        LoginStatus status = userService.processLogin(data.getUsername(), data.getPassword());

        switch (status) {
            case SUCCESS:

                // Take old information from user to return

                User user = userService.getUserByUsername(data.getUsername()).get();
                return ResponseEntity.ok(new AuthResponse("success", "Welcome!", user.getUsername(), user.getRole()));

            case USER_NOT_FOUND:
                return ResponseEntity.status(404).body("Error: Username does not exist.");

            case WRONG_PASSWORD:
                return ResponseEntity.status(401).body("Error: Incorrect password. Please try again.");

            default:
                return ResponseEntity.status(500).body("An unknown error occurred.");
        }
    }
}