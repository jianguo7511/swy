package com.apiyoo.anthorization.swy.controller;


import com.apiyoo.anthorization.swy.entity.ApiyooProduct;
import com.apiyoo.anthorization.swy.entity.ProductSku;
import com.apiyoo.anthorization.swy.entity.ShoppingCart;
import com.apiyoo.anthorization.swy.service.ApiyooProductService;
import com.apiyoo.anthorization.swy.service.ProductSkuService;
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
 * product_sku 前端控制器
 * </p>
 *
 * @author swy
 * @since 2020-03-26
 */
@RestController
@RequestMapping("/product")
public class ProductSkuController {


    @Autowired
    ProductSkuService productSkuService;
    /**
     * 根据productId查出sku信息
     * @param productId
     * @return
     */
    @GetMapping("/getProductSkuList/{productId}")
    public ServiceResult queryProudctListByCateName(@PathVariable String productId) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //查出产品列表
        List<ProductSku> productSkuList = productSkuService.lambdaQuery().eq(ProductSku::getProductId,productId).list();

        resultMap.put("skuList",productSkuList);
        System.out.println("skuList=="+resultMap);
        ServiceResult serviceResult = ServiceResult.success(resultMap);
        return serviceResult;
    }
}
