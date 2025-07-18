package com.wechat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
public class HouseAdminServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(HouseAdminServiceApplication.class, args);
    }
}