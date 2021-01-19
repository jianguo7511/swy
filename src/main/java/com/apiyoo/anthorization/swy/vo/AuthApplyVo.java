package com.apiyoo.anthorization.swy.vo;

import lombok.Data;

@Data
public class AuthApplyVo {

    private String aid;
    /**
     * 公司名称
     */
    private String companyname;
//    /**
//     * 单位地址
//     */
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
     * 提交人
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
     * 提交时间 //20200303
     */
    private String subtime;
    /**
     * 更新时间
     */
    private String updatetime;
    /**
     * 申请状态
     */
    private int applystatus;
    /**
     * 申请状态名称
     */
    private String appstaname;
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
    /**
     * 图片链接1
     */
    private String src1;
    /**
     * 图片链接2
     */
    private String src2;

    /**
     * 压缩图片链接1 80*80 size
     */
    private String src1_press;
    /**
     * 压缩图片链接2 80*80 size
     */
    private String src2_press;

    private String add;

}
