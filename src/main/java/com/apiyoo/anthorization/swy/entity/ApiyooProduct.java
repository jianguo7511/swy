package com.apiyoo.anthorization.swy.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 商品表
 * </p>
 *
 * @author swy
 * @since 2020-03-26
 */
@Data
public class ApiyooProduct implements Serializable {

    /**
     * 商品Id
     */
    @TableId("productId")
    private String productId;

    /**
     * 商品名称
     */
    @TableField("productName")
    private String productName;

    /**
     * 商品类别编码
     */
    @TableField("productCategory")
    private String productCategory;

    /**
     * 商品类别名称
     */
    @TableField("productCatName")
    private String productCatName;

    /**
     * 商品卖点(副标题)
     */
    @TableField("productTitle")
    private String productTitle;

    /**
     * 商品价格
     */
    @TableField("productPrice")
    private BigDecimal productPrice;

    /**
     * 商品状态 2已上架 4已下架
     */
    @TableField("productStatus")
    private String productStatus;

    /**
     * 商品状态名称
     */
    @TableField("productStaName")
    private String productStaName;

    /**
     * 商品主图链接
     */
    @TableField("producImgPath")
    private String producImgPath;

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
