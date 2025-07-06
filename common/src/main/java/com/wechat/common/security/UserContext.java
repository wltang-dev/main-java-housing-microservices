package com.wechat.common.security;

public class UserContext {

    private static final ThreadLocal<String> usernameHolder = new ThreadLocal<>();
    private static final ThreadLocal<String> roleHolder = new ThreadLocal<>();
    private static final ThreadLocal<String> userIdHolder = new ThreadLocal<>();

    public static void set(String username, String role) {
        usernameHolder.set(username);
        roleHolder.set(role);
    }

    public static void set(String userId, String username, String role) {
        userIdHolder.set(userId);
        usernameHolder.set(username);
        roleHolder.set(role);
    }

    public static String getUsername() {
        return usernameHolder.get();
    }

    public static String getRole() {
        return roleHolder.get();
    }

    public static String getUserId() {
        return userIdHolder.get();
    }

    public static void clear() {
        userIdHolder.remove();
        usernameHolder.remove();
        roleHolder.remove();
    }
}

