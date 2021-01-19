package com.apiyoo.anthorization.swy.service;

import com.apiyoo.anthorization.swy.entity.AiyoImg;
import com.apiyoo.anthorization.swy.entity.Company;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CompanyService extends IService<Company> {

    /**
     * 查询公司列表
     *
     * @return
     */
    List<String> queryCompanyList();
}
