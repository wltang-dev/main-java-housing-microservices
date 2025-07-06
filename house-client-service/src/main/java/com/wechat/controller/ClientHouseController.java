package com.wechat.controller;


import com.wechat.common.dto.SeckillResponse;
import com.wechat.common.enums.Role;
import com.wechat.common.model.House;
import com.wechat.common.security.LoginRequired;
import com.wechat.service.ClientHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@LoginRequired(Role.USER)
@RestController
@RequestMapping("/api/client/house")
public class ClientHouseController {

    @Autowired
    private ClientHouseService houseService;

    // 用户查看所有房源
    @GetMapping("/list")
    public List<House> listAll() {
        return houseService.listAllHouses();
    }

    //解决
    @PostMapping("/seckill/{houseId}")
    public ResponseEntity<SeckillResponse> seckill(
            @PathVariable String houseId,
            @RequestHeader("Authorization") String token) {  // get token from  request head
        SeckillResponse result = houseService.seckill(houseId, token);
        return ResponseEntity.ok(result);
    }
}
