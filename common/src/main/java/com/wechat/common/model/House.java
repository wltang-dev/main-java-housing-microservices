package com.wechat.common.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class House {

    @Id
    private String id;  // 或用 Long，根据需要调整

    private String buildingNumber;

    private String floorRoomNumber;

    private String houseType;

    private Integer price;

    private String mortgageStatus;

    private boolean available;

    private String buyerId;

    //  一定要有无参构造函数
    public House() {}


}
