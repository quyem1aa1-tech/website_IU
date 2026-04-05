package com.app.service;

import com.app.dto.SignupRequest;
import com.app.entity.LoginStatus;
import com.app.entity.User;
import com.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public LoginStatus login(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);

        // 1. Check if user exists
        if (userOpt.isEmpty()) {
            return LoginStatus.USER_NOT_FOUND;
        }

        User user = userOpt.get();

        // 2. Check if password matches // !!REQUIRE ENCRYPTION!!
        if (!user.getPassword().equals(password)) {
            return LoginStatus.WRONG_PASSWORD;
        }

        // 3. Everything is correct
        return LoginStatus.SUCCESS;

    }

    public String registerUser(SignupRequest request) {
        // 1. Check if user exists
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return "ERROR: Username already taken";
        }

        // 2. Create new User entity
        User user = new User();
        user.setUsername(request.getUsername());
        user.setFullName(request.getFullName());
        user.setRole(request.getRole());

        // 3. SECURE THE PASSWORD // !!REQUIRE ENCRYPTION!!
        user.setPassword(request.getPassword());

        // 4. Save to SQLite
        userRepository.save(user);
        return "SUCCESS: User registered";
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }
}
