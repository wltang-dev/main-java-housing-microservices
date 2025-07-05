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

    private String saleStatus;

    private String mortgageStatus;

    //  一定要有无参构造函数
    public House() {}

    // 也可以加上全参构造
    public House(String id, String buildingNumber, String floorRoomNumber, String houseType,
                 Integer price, String saleStatus, String mortgageStatus) {
        this.id = id;
        this.buildingNumber = buildingNumber;
        this.floorRoomNumber = floorRoomNumber;
        this.houseType = houseType;
        this.price = price;
        this.saleStatus = saleStatus;
        this.mortgageStatus = mortgageStatus;
    }


}
