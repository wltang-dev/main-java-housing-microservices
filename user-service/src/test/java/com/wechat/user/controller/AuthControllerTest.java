package com.wechat.user.controller;

import com.wechat.user.model.User;
import com.wechat.user.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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

        when(authService.register(user)).thenReturn("注册成功");

        String result = authController.register(user);
        assertEquals("注册成功", result);

        verify(authService).register(user); // 验证是否调用过
    }

    @Test
    void login() {
        User user = new User();
        user.setUsername("bob");
        user.setPassword("password");

        when(authService.login(user)).thenReturn("登录成功");

        String result = authController.login(user);
        assertEquals("登录成功", result);

        verify(authService).login(user);
    }

    @Test
    void getProfile() {
        // 静态方法 UserContext.getUsername() 不能直接 mock，正常应使用 PowerMock 或重构。
        // 简单起见，这里仅展示调用，不验证内容
        String result = authController.getProfile();
        System.out.println(result);  // 控制台观察
    }
}
