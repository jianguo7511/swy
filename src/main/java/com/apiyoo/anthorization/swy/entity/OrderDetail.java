package com.apiyoo.anthorization.swy.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author swy
 * @since 2020-03-26
 */
@Data
public class OrderDetail implements Serializable {

    /**
     * 订单明细Id
     */
    private String pid;

    /**
     * 商品Id
     */
    @TableField("productId")
    private String productId;

    /**
     * 商品名称
     */
    @TableField("productName")
    private String productName;

    /**
     * skuId
     */
    @TableField("skuId")
    private String skuId;

    /**
     * sku名称
     */
    @TableField("skuName")
    private String skuName;

    /**
     * 图片链接
     */
    @TableField("skuImg")
    private String skuImg;

    /**
     * 商品编码
     */
    @TableField("buinessId")
    private String buinessId;

    /**
     * 销售价格
     */
    @TableField("salePrice")
    private BigDecimal salePrice;
    /**
     * 数量
     */
    @TableField("quantity")
    private Integer quantity;

    /**
     * 总金额
     */
    private BigDecimal amount;
    /**
     * 订单id
     */
    @TableField("orderId")
    private String orderId;

    /**
     * 备用1
     */
    private String back1;

    /**
     * 备用2
     */
    private String back2;

    /**
     * 备用3
     */
    private String back3;

    /**
     * 删除标记0 未删除 1已删除
     */
    @TableField("delFlag")
    private String delFlag;


}
