package com.apiyoo.anthorization.swy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Company implements Serializable {
    /**
     * 公司ID
     */
    private String comid;
    /**
     * 公司名称
     */
    private String copanyname;
    /**
     * 创建时间
     */
    private String creattime;
    /**
     * 删除标记
     */
    private String delflag;
}
