package com.wechat.common.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class House {

    @Id
    private String id;
    private String title;
    private String description;
    private boolean isAvailable;
    private String buyerId;      // 被谁抢到了


}
