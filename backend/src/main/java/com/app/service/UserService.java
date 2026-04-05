package com.app.service;

import com.app.entity.LoginStatus;
import com.app.entity.User;
import com.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Get all users from the database.
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Find a specific user by their username.
     * Useful for login and profile viewing.
     */
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Professional Authentication Logic
     * Returns the User object if credentials are valid, otherwise returns empty.
     */
    /**
     * Advanced Login Logic to differentiate error types
     */
    public LoginStatus processLogin(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);

        // 1. Check if user exists
        if (userOpt.isEmpty()) {
            return LoginStatus.USER_NOT_FOUND;
        }

        User user = userOpt.get();

        // 2. Check if password matches
        if (!user.getPassword().equals(password)) {
            return LoginStatus.WRONG_PASSWORD;
        }

        // 3. Everything is correct
        return LoginStatus.SUCCESS;
    }

    /**
     * Create a new user (Student or Teacher)
     */
    public User saveUser(User user) {
        return userRepository.save(user);
    }

}