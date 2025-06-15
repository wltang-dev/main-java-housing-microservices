package com.wechat.common.dto;


public class UserDTO {
    private String id;
    private String username;
    private String role;

    // 构造函数
    public UserDTO() {}
    public UserDTO(String username, String role) {
        this.username = username;
        this.role = role;
    }


    // Getter / Setter


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

