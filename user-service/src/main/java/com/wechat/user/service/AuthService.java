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

        // 判断用户名是否存在
        if (userRepository.findByUsername(user.getUsername()) != null) {
            result.put("status", "fail");
            result.put("message", "用户名已存在");
            return result;
        }

        // 加密密码
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // 默认角色
        if (user.getRole() == null) {
            user.setRole(Role.USER);
        }

        // 保存用户
        userRepository.save(user);

        result.put("status", "success");
        result.put("message", "注册成功");
        return result;
    }


    public Map<String, Object> login(User user) {
        User dbUser = userRepository.findByUsername(user.getUsername());
        Map<String, Object> result = new HashMap<>();

        if (dbUser == null) {
            result.put("status", "fail");
            result.put("message", "用户不存在");
            return result;
        }

        if (!passwordEncoder.matches(user.getPassword(), dbUser.getPassword())) {
            result.put("status", "fail");
            result.put("message", "密码错误");
            return result;
        }

        String token = JwtUtil.generateToken(dbUser.getUsername(), String.valueOf(dbUser.getRole()), dbUser.getId());

        result.put("status", "success");
        result.put("token", token);
        result.put("message", "登录成功");
        return result;
    }

}
