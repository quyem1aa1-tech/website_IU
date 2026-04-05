package com.app.controller;

import com.app.entity.LoginData;
import com.app.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public String login(@RequestBody LoginData data) {
        System.out.println("CAlled");
        return authService.login(data.getUsername(), data.getPassword());
    }
}