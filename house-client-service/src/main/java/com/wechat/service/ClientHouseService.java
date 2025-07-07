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



    public SeckillResponse seckill(String houseId, String token){

        System.out.println("==> Authorization Header: " + token);
        // 1. 查询当前登录用户信息，因为前端传过来的token是1个纯字符串，所以需要拼接一下。
        UserDTO user = null;
        try {
            user = userClient.getProfile(token);
        } catch (Exception e) {
            e.printStackTrace(); // 打印看看是不是 404、连接失败等
        }
        if (user == null) {
            return new SeckillResponse("fail", "请先登录后再抢房~", null);
        }

        if (!Role.USER.equals(user.getRole())) {
            return new SeckillResponse("fail", "你没有资格参与该抢房活动", null);
        }

        String userId = user.getId(); // 或用 ID
        //2. 分布式锁
        String lockKey = "lock:house:" + houseId;
        RLock lock = redissonClient.getLock(lockKey);

        try {
            // 最多等2秒，锁定时间10秒
            if (lock.tryLock(2, 10, TimeUnit.SECONDS)) {
                Optional<House> houseOpt = houseRepository.findById(houseId);
                if (!houseOpt.isPresent()) return new SeckillResponse("fail", "房源不存在~", null);

                House house = houseOpt.get();
                if (!house.isAvailable()) return new SeckillResponse("fail", "已经被抢光了~", null);

                house.setAvailable(false);
                house.setBuyerId(userId);
                houseRepository.save(house);
                String paymentUrl = "/payment?houseId=" + houseId;
                return new SeckillResponse("success", "抢房成功，跳转支付页", paymentUrl);
            } else {
                return new SeckillResponse("fail", "系统繁忙，请稍后再试~", null);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return new SeckillResponse("fail", "\"系统异常~\"", null);
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }




}

