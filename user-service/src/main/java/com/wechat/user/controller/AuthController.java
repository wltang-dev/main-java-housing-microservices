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

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody User user) {
        return authService.register(user);
    }


    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody User user) {
        return authService.login(user);
    }

    @LoginRequired(Role.USER)
    @GetMapping("/profile")
    public UserDTO getProfile(@RequestHeader("Authorization") String token) {
        System.out.println("到了");
        // 1. 去掉前缀 Bearer（注意大小写和空格）
        if (token.startsWith("Bearer ")) {
            token = token.substring(7).trim();
        }
        System.out.println("解析前 token = [" + token + "]");
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
