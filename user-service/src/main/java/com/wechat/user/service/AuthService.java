package com.wechat.user.service;

import com.wechat.common.enums.Role;
import com.wechat.common.util.JwtUtil;
import com.wechat.user.model.User;
import com.wechat.user.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String register(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            return "用户名已存在";
        }

        // 加密密码
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // 默认角色：普通用户
        if (user.getRole() == null) {
            user.setRole(Role.USER);
        }

        userRepository.save(user);
        return "注册成功";
    }

    public String login(User user) {
        User dbUser = userRepository.findByUsername(user.getUsername());
        if (dbUser == null) {
            return "用户不存在";
        }

        // 校验密码
        if (!passwordEncoder.matches(user.getPassword(), dbUser.getPassword())) {
            return "密码错误";
        }

        // 登录成功，返回 JWT
        return  JwtUtil.generateToken(dbUser.getUsername(), String.valueOf(dbUser.getRole()), dbUser.getId());

    }

}
