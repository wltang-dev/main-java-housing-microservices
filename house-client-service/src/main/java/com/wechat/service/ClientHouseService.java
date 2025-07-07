package com.wechat.service;

import com.wechat.client.feign.UserClient;
import com.wechat.common.dto.SeckillResponse;
import com.wechat.common.dto.UserDTO;
import com.wechat.common.enums.Role;
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
    private UserClient userClient;

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

    public SeckillResponse seckill(String houseId, String token) {
        System.out.println("==> Authorization Header: " + token);

        // 1. Retrieve current logged-in user information.
        // Because the token from the frontend is a raw string, we need to format it.
        UserDTO user = null;
        try {
            user = userClient.getProfile(token);
        } catch (Exception e) {
            e.printStackTrace(); // Print to check for 404, connection failures, etc.
        }

        if (user == null) {
            return new SeckillResponse("fail", "Please log in before attempting to grab a house.", null);
        }

        if (!Role.USER.equals(user.getRole())) {
            return new SeckillResponse("fail", "You are not eligible to participate in this event.", null);
        }

        String userId = user.getId(); // Or use ID directly

        // 2. Distributed locking
        String lockKey = "lock:house:" + houseId;
        RLock lock = redissonClient.getLock(lockKey);

        try {
            // Wait up to 2 seconds, lock for 10 seconds
            if (lock.tryLock(2, 10, TimeUnit.SECONDS)) {
                Optional<House> houseOpt = houseRepository.findById(houseId);
                if (!houseOpt.isPresent()) return new SeckillResponse("fail", "House not found.", null);

                House house = houseOpt.get();
                if (!house.isAvailable()) return new SeckillResponse("fail", "This house has already been taken.", null);

                house.setAvailable(false);
                house.setBuyerId(userId);
                houseRepository.save(house);
                String paymentUrl = "/payment?houseId=" + houseId;
                return new SeckillResponse("success", "House successfully reserved. Redirecting to payment page.", paymentUrl);
            } else {
                return new SeckillResponse("fail", "System is busy, please try again later.", null);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return new SeckillResponse("fail", "System error.", null);
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }
}
