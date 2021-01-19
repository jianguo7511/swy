package com.apiyoo.anthorization.swy.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * product_sku
 * </p>
 *
 * @author swy
 * @since 2020-03-26
 */
@Data
public class ProductSku implements Serializable {

    /**
     * 主键
     */
    @TableId("skuId")
    private String skuId;

    /**
     * 名称
     */
    @TableField("skuName")
    private String skuName;

    /**
     * 价格
     */
    @TableField("skuPrice")
    private BigDecimal skuPrice;

    /**
     * 状态
     */
    @TableField("skuStatus")
    private String skuStatus;

    /**
     * 商家编码
     */
    @TableField("shopCode")
    private String shopCode;

    /**
     * 图片链接Id
     */
    @TableField("skuImgPath")
    private String skuImgPath;

    /**
     * 条形码
     */
    @TableField("skuBarCode")
    private String skuBarCode;

    /**
     * 库存
     */
    @TableField("skuStock")
    private Integer skuStock;

    /**
     * 序列号(排序)
     */
    @TableField("skuSeq")
    private String skuSeq;

    /**
     * 上架时间
     */
    @TableField("startTime")
    private LocalDateTime startTime;

    /**
     * 下架时间
     */
    @TableField("stopTime")
    private LocalDateTime stopTime;

    /**
     * 商品id
     */
    @TableField("productId")
    private String productId;

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
