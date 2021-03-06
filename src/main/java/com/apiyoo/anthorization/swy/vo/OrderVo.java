package com.apiyoo.anthorization.swy.vo;

import com.apiyoo.anthorization.swy.entity.OrderDetail;
import com.apiyoo.anthorization.swy.entity.ProductSku;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderVo {
    /**
     * 订单Id
     */

    private String orderId;

    /**
     * 收货人
     */
    private String receiver;

    /**
     * 联系电话
     */

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

    private String addrDetail;

    /**
     * 完整地址
     */

    private String fullAddr;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 下单人
     */
    private String orderName;

    /**
     * 卖家留言
     */
    private String remark;

    /**
     * 订单状态码
     */
    private String statusCode;

    /**
     * 订单状态名称
     */
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
    private String productAmount;

    /**
     * 商品总金额
     */
    private BigDecimal productTotalSum;

    /**
     * 支付金额
     */
    private BigDecimal payAmount;

    /**
     * 0自提1快递
     */
    private String distribution;

    /**
     * 创建时间
     */
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
    private String payImg;


    private List<OrderDetail> orderDetails;
}
