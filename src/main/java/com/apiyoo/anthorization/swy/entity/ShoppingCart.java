package com.apiyoo.anthorization.swy.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 购物车表
 * </p>
 *
 * @author swy
 * @since 2020-03-31
 */
@Data
public class ShoppingCart implements Serializable {

    /**
     * 主键
     */
    @TableId("cartId")
    private String cartId;


    /**
     * 产品Id
     */
    @TableField("productId")
    private String productId;

    /**
     * 商品价格
     */
    @TableField("skuPrice")
    private BigDecimal skuPrice;


    /**
     * 商品名称
     */
    @TableField("skuName")
    private String skuName;

    /**
     * sku链接
     */
    @TableField("skuImg")
    private String skuImg;
    /**
     * skuId
     */
    @TableField("skuId")
    private String skuId;

    /**
     * 商品数量
     */
    private Integer quantity;

    /**
     * 用户Id
     */
    @TableField("userId")
    private String userId;

    /**
     * 商家Id
     */
    @TableField("shopId")
    private String shopId;

    /**
     * 删除标记0 未删除 1已删除
     */
    @TableField("delFlag")
    private String delFlag;


}
