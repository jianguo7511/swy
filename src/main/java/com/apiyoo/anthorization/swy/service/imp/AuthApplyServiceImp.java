package com.apiyoo.anthorization.swy.service.imp;

import com.apiyoo.anthorization.swy.dto.AuthApplyCheckDto;
import com.apiyoo.anthorization.swy.dto.AuthApplyDto;
import com.apiyoo.anthorization.swy.entity.AuthApply;
import com.apiyoo.anthorization.swy.entity.Authorization;
import com.apiyoo.anthorization.swy.mapper.AuthApplyMapper;
import com.apiyoo.anthorization.swy.mapper.AuthorizationMapper;
import com.apiyoo.anthorization.swy.service.AuthApplyService;
import com.apiyoo.anthorization.swy.service.AuthorService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class AuthApplyServiceImp extends ServiceImpl<AuthApplyMapper, AuthApply> implements AuthApplyService {

    @Resource
    private AuthApplyMapper authApplyMapper;


    @Override
    public List<AuthApply> queryApplyList(String company_name, String apply_user) {
        return authApplyMapper.queryAuthApplyList(company_name, apply_user);
    }

    @Override
    public AuthApply queryAuthApplyOne(String aid) {
        return authApplyMapper.queryAuthApplyOne(aid);
    }

//    @Override
//    public int updateAuthApplyByPid(int applystatus, String refreason, String appstaname, String proinsid) {
//        return authApplyMapper.updateAuthApplyByPid(applystatus, refreason, appstaname, proinsid);
//    }


    @Override
    public int updateAuthApplyByPid(int applystatus, String refreason, String appstaname, String proinsid) {
        return authApplyMapper.updateAuthApplyByPid(
                applystatus,
                refreason,
                appstaname,
                proinsid);
    }

    @Override
    public int updateAuthApplySuccessByPid(AuthApply authApply) {

        return authApplyMapper.updateAuthApplySuccessByPid(authApply.getApplystatus(),
                authApply.getRefreason(),
                authApply.getAppstaname(),
                authApply.getProinsid(),
                authApply.getAptimeblock(),
                authApply.getAuthcode(),
                authApply.getAuthtime());
    }

    @Override
    public List<AuthApply> queryAuthedList(String creditcode, String orgname) {
        return authApplyMapper.queryAuthedList(creditcode,orgname);
    }

    @Override
    public AuthApply queryAuthApplyStatus(String proinsid) {
        QueryWrapper<AuthApply> ew = new QueryWrapper<AuthApply>();
        ew.eq("proinsid",proinsid);
        return authApplyMapper.selectOne(ew);
    }

    @Override
    public AuthApply queryAuthApplyByProinsid(String proinsid) {
        return authApplyMapper.queryAuthApplyByProinsid(proinsid);
    }
}

