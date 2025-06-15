package com.wechat.service;


import com.wechat.client.feign.HouseClientClient;
import com.wechat.common.dto.UserDTO;
import com.wechat.common.model.House;
import com.wechat.repository.HouseRepository;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class ClientHouseService {
    @Autowired
    private HouseRepository houseRepository;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private HouseClientClient userClient;

    public List<House> listAllHouses() {
        return houseRepository.findAll();
    }



    public boolean grabHouse(String houseId) {
        Optional<House> houseOpt = houseRepository.findById(houseId);
        if (houseOpt.isPresent() && houseOpt.get().isAvailable()) {
            houseOpt.get().setAvailable(false);
            return true;
        }
        return false;
    }



    public String seckill(String houseId, String token) {

        // 1. 查询当前登录用户信息
        UserDTO user = userClient.getProfile(token);
        if (user == null) {
            return "请先登录后再抢房~";
        }

        if (!"USER".equals(user.getRole())) {
            return "你没有资格参与该抢房活动";
        }

        String userId = user.getId(); // 或用 ID
        //2. 分布式锁
        String lockKey = "lock:house:" + houseId;
        RLock lock = redissonClient.getLock(lockKey);

        try {
            // 最多等2秒，锁定时间10秒
            if (lock.tryLock(2, 10, TimeUnit.SECONDS)) {
                Optional<House> houseOpt = houseRepository.findById(houseId);
                if (!houseOpt.isPresent()) return "房源不存在~";

                House house = houseOpt.get();
                if (!house.isAvailable()) return "已经被抢光了~";

                house.setAvailable(false);
                house.setBuyerId(userId);
                houseRepository.save(house);

                return "恭喜你抢到了！";
            } else {
                return "系统繁忙，请稍后再试~";
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return "系统异常~";
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }




}

