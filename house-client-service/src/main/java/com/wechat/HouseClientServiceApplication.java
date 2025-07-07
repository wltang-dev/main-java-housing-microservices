package com.wechat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.wechat.client.feign")
@EnableDiscoveryClient
public class HouseClientServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(HouseClientServiceApplication.class, args);
    }
}