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
    * 包裹表
    * </p>
*
* @author swy
* @since 2020-03-26
*/
    @Data
    public class Package implements Serializable  {

            /**
            * 包裹Id
            */
            @TableId("packId")
    private String packId;

            /**
            * 订单Id
            */
        @TableField("orderId")
    private String orderId;

            /**
            * 包裹编号
            */
        @TableField("packCode")
    private String packCode;

            /**
            * 快递单号
            */
        @TableField("expressNum")
    private String expressNum;

            /**
            * 承运商（快递公司）
            */
        @TableField("expressOrgName")
    private String expressOrgName;

            /**
            * 快递代码
            */
        @TableField("expressCode")
    private String expressCode;

            /**
            * 状态
            */
    private String status;

            /**
            * 快递信息
            */
        @TableField("expressMsg")
    private String expressMsg;

            /**
            * 收件人
            */
    private String receiver;

            /**
            * 收件人电话
            */
        @TableField("receiverTel")
    private String receiverTel;

            /**
            * 完整地址
            */
        @TableField("FullAddr")
    private String FullAddr;

            /**
            * 数量
            */
    private Integer quantity;

            /**
            * 总金额
            */
    private BigDecimal amount;

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
