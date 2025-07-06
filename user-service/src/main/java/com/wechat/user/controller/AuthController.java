package com.wechat.user.controller;

import com.wechat.common.dto.UserDTO;
import com.wechat.common.enums.Role;
import com.wechat.common.security.LoginRequired;

import com.wechat.common.util.JwtUtil;
import com.wechat.user.model.User;

import com.wechat.user.service.AuthService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        return authService.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        return authService.login(user);
    }

    @LoginRequired(Role.USER)
    @GetMapping("/profile")
    public UserDTO getProfile(@RequestHeader("Authorization") String token) {
        Claims claims = JwtUtil.parseToken(token);

        String username = claims.getSubject();
        String role = claims.get("role", String.class);
        Long userId = claims.get("id", Long.class);

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(username);
        userDTO.setRole(Role.valueOf(role));
        userDTO.setId(String.valueOf(userId));

        return userDTO;
    }


}
