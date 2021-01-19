package com.apiyoo.anthorization.swy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("apiyoo_images")
public class AiyoImg implements Serializable {

    /**
     * 主键
     * 图片ID
     */
    @TableId(type = IdType.UUID)
    private String pid;
    /**
     * 图片url
     */
    private String imgurl;

    /**
     * 图片url
     */
    private String imgpress;

    /**
     * 授权表id
     */
    private String aid;

    /**
     * '图片宽度
     */
    private String width;
    /**
     * '图片高度'
     */
    private String height;
    /**
     * '图片大小'
     */
    private String size;
    /**
     * '图片类型'
     */
    private String type;
    /**
     * '创建时间'
     */
    private String creatime;
    /**
     * '删除标记'
     */
    private String delflag;


}
