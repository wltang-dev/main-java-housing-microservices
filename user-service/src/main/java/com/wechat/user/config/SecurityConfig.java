package com.wechat.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()  // 放行登录注册
                        .anyRequest().permitAll()  // 其余接口都放行（后期可改为 .authenticated()）
                )
                .csrf(csrf -> csrf.disable()); // ✅ 推荐写法，防止警告

        return http.build();
    }
}
