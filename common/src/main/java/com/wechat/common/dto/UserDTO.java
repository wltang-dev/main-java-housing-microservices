package com.wechat.common.dto;


import com.wechat.common.enums.Role;

public class UserDTO {
    private String id;
    private String username;
    private Role role;

    // 构造函数
    public UserDTO() {}

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
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

}

