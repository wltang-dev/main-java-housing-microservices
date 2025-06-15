package com.wechat.service;

import com.wechat.common.model.House;
import com.wechat.repository.HouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AdminHouseService {

    @Autowired
    private HouseRepository houseRepository;

    public void publishHouse(House house) {
        house.setId(UUID.randomUUID().toString());
        house.setAvailable(true);
        houseRepository.save(house);
    }


    public List<House> listAllHouses() {
        return houseRepository.findAll();
    }
}
