package com.apiyoo.anthorization.swy.vo;

import com.apiyoo.anthorization.swy.entity.ShoppingCart;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class preOrderVo {
    /**
     * 购物车对象集合
     */
    private List<ShoppingCart> cartList;

    /**
     * 配货方式
     */
    private String deliveryMode;
    /**
     * 订单总金额
     */
    private BigDecimal amount;
    /**
     * 运费
     */
    private BigDecimal freight;
    /**
     * 打款截图
     */
    private String src1;

}
