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
 *
 * </p>
 *
 * @author swy
 * @since 2020-03-26
 */
@Data
public class ApiyooOrder implements Serializable {


    /**
     * 订单Id
     */
    @TableId("orderId")
    private String orderId;

    /**
     * 收货人
     */
    private String receiver;

    /**
     * 联系电话
     */
    @TableField("receiverTel")
    private String receiverTel;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 区
     */
    private String area;

    /**
     * 详细地址
     */
    @TableField("addrDetail")
    private String addrDetail;

    /**
     * 完整地址
     */
    @TableField("fullAddr")
    private String fullAddr;

    /**
     * 用户ID
     */
    @TableField("userId")
    private String userId;

    /**
     * 下单人
     */
    @TableField("orderName")
    private String orderName;

    /**
     * 卖家留言
     */
    private String remark;

    /**
     * 订单状态码
     */
    @TableField("statusCode")
    private String statusCode;

    /**
     * 订单状态名称
     */
    @TableField("statusName")
    private String statusName;

    /**
     * 运费
     */
    private Integer freight;

    /**
     * 商品数量
     */
    private Integer quantity;

    /**
     * 商品金额
     */
    @TableField("productAmount")
    private String productAmount;

    /**
     * 商品总金额
     */
    @TableField("productTotalSum")
    private BigDecimal productTotalSum;

    /**
     * 支付金额
     */
    @TableField("payAmount")
    private BigDecimal payAmount;

    /**
     * 0自提1快递
     */
    private String distribution;

    /**
     * 创建时间
     */
    @TableField("creatTime")
    private String creatTime;

    /**
     * 删除标记 0未删除 1已删除
     */
    private String delflag;

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
     * 付款截图
     */
    @TableField("payImg")
    private String payImg;
}
