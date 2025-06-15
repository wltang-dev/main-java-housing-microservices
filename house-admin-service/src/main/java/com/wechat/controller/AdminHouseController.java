package com.wechat.controller;


import com.wechat.common.enums.Role;
import com.wechat.common.model.House;
import com.wechat.common.security.LoginRequired;
import com.wechat.service.AdminHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@LoginRequired(Role.USER)
@RestController
@RequestMapping("/api/admin/house")
public class AdminHouseController {

    @Autowired
    private AdminHouseService adminhouseService;

    // 管理员发布房源
    @PostMapping("/publish")
    public ResponseEntity<String> publish(@RequestBody House house) {
        adminhouseService.publishHouse(house);
        return ResponseEntity.ok("房源发布成功");
    }

    // 管理员查看房源列表（可拓展分页等）
    @GetMapping("/list")
    public List<House> listAll() {
        return adminhouseService.listAllHouses();
    }
}

