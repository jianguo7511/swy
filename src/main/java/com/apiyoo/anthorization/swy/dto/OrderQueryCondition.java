package com.apiyoo.anthorization.swy.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class OrderQueryCondition implements Serializable {

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 下单人Id
     */
    private String userId;
}
