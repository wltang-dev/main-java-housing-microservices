package com.wechat.user.controller;

import com.wechat.common.enums.Role;
import com.wechat.common.security.LoginRequired;
import com.wechat.common.security.UserContext;
import com.wechat.user.model.User;

import com.wechat.user.service.AuthService;
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
    public String getProfile() {
        String username = UserContext.getUsername();
        return "当前登录用户：" + username;
    }
}
