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
                    .csrf(csrf -> csrf.disable()) // 禁用 CSRF
                    .headers(headers -> headers
                            .frameOptions(frame -> frame.disable()) // 允许 iframe 加载
                    )
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers("/h2-console/**", "/api/auth/**").permitAll() // 放行 H2 Console 和注册登录接口
                            .anyRequest().authenticated()
                    );

            return http.build();
        }
}


