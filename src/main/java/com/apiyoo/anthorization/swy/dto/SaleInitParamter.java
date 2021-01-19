package com.apiyoo.anthorization.swy.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class SaleInitParamter implements Serializable {

    private String productId;

    private String skuId;

    private String skuName;

    private Integer skuPrice;

    private Integer quantity;

    private String userId;
    //购物车增加，减少标志  0增加 1减少
    private  String updateFlag;

}
