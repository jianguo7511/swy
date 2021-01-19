package com.apiyoo.anthorization.swy.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 审批参数回传
 */
@Data
public class AuthApplyCheckDto implements Serializable {
    @NotBlank(message = "【aid】不能为空.")
    private String aid;
    /**
     * 申请状态 1是已通过 2已拒绝
     */
    @NotBlank(message = "【审批状态】不能为空.")
    private String applystatus;
    /**
     * 审批人
     */
    @NotBlank(message = "【审批人】不能为空.")
    private String approval;

    /**
     * 授权时间区间   20200311-20200331
     */
    private String aptimeblock;
}
