package com.app.service;

import com.app.entity.User;
import com.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public String login(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);

        if (userOpt.isPresent()) {
            User user = userOpt.get();

            // In a real project, use BCrypt for password hashing!)

            if (user.getPassword().equals(password)) {
                return "Login successful! Welcome " + user.getFullName() + " (" + user.getRole() + ")";
            }
        }
        return "Invalid username or password!";


        
    }

    
}
