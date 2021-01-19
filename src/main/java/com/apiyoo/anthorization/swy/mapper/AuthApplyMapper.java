package com.apiyoo.anthorization.swy.mapper;

import com.apiyoo.anthorization.swy.entity.AuthApply;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AuthApplyMapper extends BaseMapper<AuthApply> {


    List<AuthApply> queryAuthApplyList(@Param("company_name") String company_name,
                                       @Param("apply_user") String apply_user);

    AuthApply queryAuthApplyOne(@Param("aid") String aid);

    AuthApply queryAuthApplyByProinsid(@Param("proinsid") String proinsid);

    int updateAuthApplyByPid(@Param("applystatus") int applystatus,
                             @Param("refreason") String refreason,
                             @Param("appstaname") String appstaname,
                             @Param("proinsid") String proinsid);

    int updateAuthApplySuccessByPid(@Param("applystatus") int applystatus,
                                    @Param("refreason") String refreason,
                                    @Param("appstaname") String appstaname,
                                    @Param("proinsid") String proinsid,
                                    @Param("aptimeblock") String aptimeblock,
                                    @Param("authcode") String authcode,
                                    @Param("authtime") String authtime);

    List<AuthApply> queryAuthedList(@Param("creditcode") String creditcode,
                                       @Param("orgname") String orgname);

}
