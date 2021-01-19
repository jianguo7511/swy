package com.apiyoo.anthorization.swy.service.imp;

import com.apiyoo.anthorization.swy.entity.Authorization;
import com.apiyoo.anthorization.swy.mapper.AuthorizationMapper;
import com.apiyoo.anthorization.swy.service.AuthorService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AuthorServiceImp extends ServiceImpl<AuthorizationMapper, Authorization> implements AuthorService {
    @Resource
    private AuthorizationMapper mapper;

    @Override
    public Authorization queryByAid(String aid) {
        return mapper.querybyAid(aid);

    }

    @Override
    public int insertAuthorization(Authorization a) {
        return mapper.insert(a);
    }

    @Override
    public Authorization queryByOrg(String orgname) {
        QueryWrapper<Authorization> ew = new QueryWrapper();
        ew.eq("org_name", orgname);
        Authorization a = mapper.selectOne(ew);
        return a;
    }

    @Override
    public List<Authorization> getAllAuthorization() {
        QueryWrapper<Authorization> ew = new QueryWrapper();
        //备用字段
        //ew.eq("org_name", "");
        return mapper.selectList(ew);
    }

    @Override
    public IPage<Authorization> selectPage(long page, long size) {
        IPage<Authorization> iPage = mapper.selectPage(new Page<>(page, size), null);
        return iPage;
    }
}
