package com.apiyoo.anthorization.swy.controller;


import com.apiyoo.anthorization.swy.entity.ApiyooProduct;
import com.apiyoo.anthorization.swy.entity.ShoppingCart;
import com.apiyoo.anthorization.swy.service.ApiyooProductService;
import com.apiyoo.anthorization.swy.service.ShoppingCartService;
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
 * 商品表 前端控制器
 * </p>
 *
 * @author swy
 * @since 2020-03-26
 */
@RestController
public class ApiyooProductController {
    @Autowired
    ApiyooProductService apiyooProductService;

    @Autowired
    ShoppingCartService shoppingCartService;

    /**
     * 页面初始化信息查询
     * @param userId。
     * @return
     */
    @GetMapping("/product/getProducList/{userId}")
    public ServiceResult queryProudctListByCateName(@PathVariable String userId) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //查出产品列表
        List<ApiyooProduct> apiyooProductList = apiyooProductService.lambdaQuery().list();
        //查出用户购物车列表
        List<ShoppingCart> shoppingCartList = shoppingCartService.lambdaQuery().eq(ShoppingCart::getUserId,userId).list();

        resultMap.put("products",apiyooProductList);
        resultMap.put("shopCart",shoppingCartList);
        ServiceResult serviceResult = ServiceResult.success(resultMap);
        return serviceResult;
}

    /**
     * 根据productId查询sku商品
     * @param productId
     * @return
     */
    @GetMapping("/getProduct/{productId}")
    public ServiceResult queryProudctListByproductId(@PathVariable String productId) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        apiyooProductService.lambdaQuery().eq(ApiyooProduct::getProductId,productId).list();
        ServiceResult serviceResult = ServiceResult.success(resultMap);
        return serviceResult;
    }

    /**
     * 根据分类编码查询查询商品
     * @param categoryCode
     * @return
     */
    @GetMapping("/getProductByCateCode/{categoryCode}")
    public ServiceResult queryProudctListByCategoryId(@PathVariable String categoryCode) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        List<ApiyooProduct> apiyooProductList = apiyooProductService.queryProductListByCateId(categoryCode);
        resultMap.put("products",apiyooProductList);
        ServiceResult serviceResult = ServiceResult.success(resultMap);
        System.out.println("resultMap=="+resultMap);
        return serviceResult;
    }



}
