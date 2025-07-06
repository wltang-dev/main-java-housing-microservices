package com.wechat.client.feign;

// com.wechat.client.feign.UserClient.java

import com.wechat.common.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient("user-service") // 指向 user-service
public interface UserClient {
    @GetMapping("/api/auth/profile")
    UserDTO getProfile(@RequestHeader("Authorization") String token);
}

