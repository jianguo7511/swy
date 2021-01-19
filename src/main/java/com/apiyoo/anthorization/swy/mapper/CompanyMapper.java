package com.apiyoo.anthorization.swy.mapper;

import com.apiyoo.anthorization.swy.entity.AiyoImg;
import com.apiyoo.anthorization.swy.entity.Company;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CompanyMapper extends BaseMapper<Company> {

    List<String> queryCompanyList();

}
