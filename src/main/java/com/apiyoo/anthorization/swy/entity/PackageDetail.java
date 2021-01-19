package com.apiyoo.anthorization.swy.entity;

    import com.baomidou.mybatisplus.extension.activerecord.Model;
    import com.baomidou.mybatisplus.annotation.TableId;
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
    public class PackageDetail implements Serializable {

            /**
            * 包裹明细Id
            */
            @TableId("pckdetaId")
    private String pckdetaId;

            /**
            * skuid
            */
        @TableField("skuId")
    private String skuId;

            /**
            * sku名称
            */
        @TableField("skuName")
    private String skuName;

            /**
            * 数量
            */
    private Integer quantity;

            /**
            * 包裹Id
            */
        @TableField("packId")
    private String packId;

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
