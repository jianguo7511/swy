package com.apiyoo.anthorization.swy.service;

import com.apiyoo.anthorization.swy.dto.AuthApplyCheckDto;
import com.apiyoo.anthorization.swy.dto.AuthApplyDto;
import com.apiyoo.anthorization.swy.entity.AuthApply;
import com.apiyoo.anthorization.swy.entity.Authorization;
import com.apiyoo.anthorization.swy.mapper.AuthApplyMapper;
import com.apiyoo.anthorization.swy.mapper.AuthorizationMapper;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.annotation.Resource;
import java.util.List;

public interface AuthApplyService extends IService<AuthApply> {

    /**
     * 查询授权列表
     *
     * @return
     */
    List<AuthApply> queryApplyList(String company_name, String apply_user);

    /**
     * 查询授权详情
     *
     * @param aid
     * @return
     */
    AuthApply queryAuthApplyOne(String aid);
    /**
     * 通过proinsid查询授权详情
     *
     * @param proinsid
     * @return
     */
    AuthApply queryAuthApplyByProinsid(String proinsid);

    /**
     * 更新授权状态
     *
     * @param applystatus
     * @param refreason
     * @param appstaname
     * @param proinsid
     * @return
     */
    int updateAuthApplyByPid(int applystatus,
                             String refreason,
                             String appstaname,
                             String proinsid);

    /**
     * 更新授权成功的申请信息
     * @param authApply
     * @return
     */
    int updateAuthApplySuccessByPid(AuthApply authApply);

    /**
     * 根据查creditcode，orgname询授权信息
     * @param creditcode
     * @param orgname
     * @return
     */
    List<AuthApply> queryAuthedList(String creditcode,String orgname );

    AuthApply queryAuthApplyStatus(String proinsid);
}
