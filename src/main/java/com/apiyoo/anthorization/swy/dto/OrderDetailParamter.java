package com.apiyoo.anthorization.swy.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class OrderDetailParamter implements Serializable {

    /**
     * 订单Id
     */
    @NotBlank(message = "订单编号不能为空")
    private String orderId;

    /**
     * 下单人Id
     */
    @NotBlank(message = "用户不能为空")
    private String userId;
}
