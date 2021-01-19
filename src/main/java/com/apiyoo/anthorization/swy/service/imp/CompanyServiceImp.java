package com.apiyoo.anthorization.swy.service.imp;

import com.apiyoo.anthorization.swy.config.URLConstant;
import com.apiyoo.anthorization.swy.entity.AiyoImg;
import com.apiyoo.anthorization.swy.entity.Company;
import com.apiyoo.anthorization.swy.mapper.AiyoImgMapper;
import com.apiyoo.anthorization.swy.mapper.CompanyMapper;
import com.apiyoo.anthorization.swy.service.AiyoImgService;
import com.apiyoo.anthorization.swy.service.CompanyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class CompanyServiceImp extends ServiceImpl<CompanyMapper, Company> implements CompanyService {

    @Resource
    private CompanyMapper companyMapper;

    @Override
    public List<String> queryCompanyList() {
        return companyMapper.queryCompanyList();
    }
}

