package com.apiyoo.anthorization.swy.entity;

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
    * 商品主图表
    * </p>
*
* @author swy
* @since 2020-03-26
*/
    @Data
    public class ProductImage implements Serializable{

            /**
            * 图片Id
            */
            @TableId("imgId")
    private String imgId;

            /**
            * 图片链接
            */
        @TableField("imgUrl")
    private String imgUrl;

            /**
            * 压缩图片链接
            */
        @TableField("imgUrlPress")
    private String imgUrlPress;

            /**
            * 创建时间
            */
        @TableField("creatTime")
    private LocalDateTime creatTime;

            /**
            * 删除标记 0未删除1已删除
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

}
