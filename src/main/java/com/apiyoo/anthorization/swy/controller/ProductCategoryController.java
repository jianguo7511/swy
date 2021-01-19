package com.apiyoo.anthorization.swy.controller;


import com.apiyoo.anthorization.swy.entity.ProductCategory;
import com.apiyoo.anthorization.swy.entity.ProductSku;
import com.apiyoo.anthorization.swy.service.ProductCategoryService;
import com.apiyoo.anthorization.swy.util.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品分类表 前端控制器
 * </p>
 *
 * @author swy
 * @since 2020-04-02
 */
@RestController
@RequestMapping("/category")
public class ProductCategoryController {

    @Autowired
    ProductCategoryService productCategoryService;

    @GetMapping("/getCategoryList")
    public ServiceResult getCategoryList() {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //查出产品列表
        List<ProductCategory> productCategoryList = productCategoryService.lambdaQuery().list();

        resultMap.put("cateList",productCategoryList);
        System.out.println("cateList=="+resultMap);
        ServiceResult serviceResult = ServiceResult.success(resultMap);
        return serviceResult;
    }
}
