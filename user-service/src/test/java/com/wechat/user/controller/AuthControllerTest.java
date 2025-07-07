package com.wechat.user.controller;

import com.wechat.user.model.User;
import com.wechat.user.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // 初始化 @Mock 注解的字段
    }

    @Test
    void register() {
        User user = new User();
        user.setUsername("alice");
        user.setPassword("123456");

        Map<String, Object> expected = new HashMap<>();
        expected.put("status", "success");
        expected.put("message", "注册成功");

        when(authService.register(user)).thenReturn(expected);

        Map<String, Object> result = authController.register(user);

        assertEquals("success", result.get("status"));
        assertEquals("注册成功", result.get("message"));

        verify(authService).register(user);
    }

    @Test
    void login() {
        User user = new User();
        user.setUsername("bob");
        user.setPassword("password");

        Map<String, Object> expected = new HashMap<>();
        expected.put("status", "success");
        expected.put("token", "fake-jwt-token");

        when(authService.login(user)).thenReturn(expected);

        Map<String, Object> result = authController.login(user);

        assertEquals("success", result.get("status"));
        assertEquals("fake-jwt-token", result.get("token"));

        verify(authService).login(user);
    }


    @Disabled("getProfile() 暂时跳过，需重构或引入 PowerMock 支持静态方法 mock")
    @Test
    void getProfile() {
        // ...
    }
}
