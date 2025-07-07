package com.wechat.user.service;

import com.wechat.common.enums.Role;
import com.wechat.common.util.JwtUtil;
import com.wechat.user.model.User;
import com.wechat.user.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Map<String, Object> register(User user) {
        Map<String, Object> result = new HashMap<>();

        // Check if username already exists
        if (userRepository.findByUsername(user.getUsername()) != null) {
            result.put("status", "fail");
            result.put("message", "Username already exists");
            return result;
        }

        // Encrypt password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Set default role
        if (user.getRole() == null) {
            user.setRole(Role.USER);
        }

        // Save user
        userRepository.save(user);

        result.put("status", "success");
        result.put("message", "Registration successful");
        return result;
    }

    public Map<String, Object> login(User user) {
        User dbUser = userRepository.findByUsername(user.getUsername());
        Map<String, Object> result = new HashMap<>();

        if (dbUser == null) {
            result.put("status", "fail");
            result.put("message", "User does not exist");
            return result;
        }

        if (!passwordEncoder.matches(user.getPassword(), dbUser.getPassword())) {
            result.put("status", "fail");
            result.put("message", "Incorrect password");
            return result;
        }

        String token = JwtUtil.generateToken(dbUser.getUsername(), String.valueOf(dbUser.getRole()), dbUser.getId());

        result.put("status", "success");
        result.put("token", token);
        result.put("message", "Login successful");
        return result;
    }

}
