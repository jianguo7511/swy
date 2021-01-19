package com.apiyoo.anthorization.swy.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class AuthApplyDto implements Serializable {
    /**
     * 公司id
     */
    private String company_id;
    /**
     * 单位地址
     */
    private String orgaddress;
    /**
     * 单位名称
     */
    private String orgname;
    /**
     * 行业分类
     */
    private String classification;
    /**
     * 统一社会信用代码
     */
    private String creditcode;
    /**
     * 法人
     */
    private String legalperson;
    /**
     * 联系人
     */
    private String contact;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 授权号
     */
    private String authcode;
    /**
     * 授权时间
     */
        private String authtime;
    /**
     * 备注
     */
    private String remarks;

    /**
     *提交人
     */
    private String apuser;

    /**
     *图片id
     */
//        private String img_id;
    /**
     * 提交部门
     */
    private String suborg;
    /**
     * 提交时间
     */
        private String subtime;
    /**
     * 更新时间
     */
       private String updatetime;
    /**
     * 申请状态
     */
    private String applystatus;
    /**
     * 审批人
     */
    private String approval;

    /**
     * 拒绝理由
     */
    private String refreason;

    /**
     * 授权时间区间   20200311-20200331
     */
    private String aptimeblock;

}
