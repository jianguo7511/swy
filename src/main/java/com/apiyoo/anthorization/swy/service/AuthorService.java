package com.apiyoo.anthorization.swy.service;

import com.apiyoo.anthorization.swy.entity.Authorization;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

public interface AuthorService extends IService<Authorization> {
    /**
     * 按照aid查询企业授权信息
     *
     * @param aid
     * @return
     */
    Authorization queryByAid(String aid);

    /**
     * 按照企业名称查询企业授权信息
     *
     * @param orgname
     * @return
     */
    Authorization queryByOrg(String orgname);

    public int insertAuthorization(Authorization a);


    public List<Authorization> getAllAuthorization();


    public IPage<Authorization> selectPage(long page, long size);
}
