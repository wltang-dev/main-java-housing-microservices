package com.wechat.user.model;

import com.wechat.common.enums.Role;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password; // 加密存储

    @Enumerated(EnumType.STRING)
    private Role role; // USER / ADMIN


}
