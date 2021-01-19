package com.apiyoo.anthorization.swy.entity;

import com.baomidou.mybatisplus.annotation.TableName;
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
 * 商品分类表
 * </p>
 *
 * @author swy
 * @since 2020-04-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ProductCategory extends Model<ProductCategory> {

    private static final long serialVersionUID = 1L;

    /**
     * 分类Id
     */
    @TableId("categoryId")
    private String categoryId;

    /**
     * 分类编码
     */
    @TableField("categoryCode")
    private  String categoryCode;

    /**
     * 分类名称
     */
    @TableField("categoryName")
    private String categoryName;

    /**
     * 创建时间
     */
    @TableField("creatTime")
    private LocalDateTime creatTime;

    /**
     * 删除标记
     */
    @TableField("delFlag")
    private String delFlag;


    @Override
    protected Serializable pkVal() {
        return this.categoryId;
    }

}
