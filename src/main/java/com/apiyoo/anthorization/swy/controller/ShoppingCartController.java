package com.apiyoo.anthorization.swy.controller;


import cn.hutool.core.bean.BeanUtil;
import com.apiyoo.anthorization.swy.dto.SaleInitParamter;
import com.apiyoo.anthorization.swy.entity.ApiyooProduct;
import com.apiyoo.anthorization.swy.entity.ProductSku;
import com.apiyoo.anthorization.swy.entity.ShoppingCart;
import com.apiyoo.anthorization.swy.service.ApiyooProductService;
import com.apiyoo.anthorization.swy.service.ProductSkuService;
import com.apiyoo.anthorization.swy.service.ShoppingCartService;
import com.apiyoo.anthorization.swy.util.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 * 购物车 前端控制器
 * </p>
 *
 * @author swy
 * @since 2020-03-31
 */
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private ProductSkuService productSkuService;

    @GetMapping("/query/{userId}")
    public ServiceResult queryShoppingCartByUserId(@PathVariable String userId) {
        ServiceResult serviceResult;
        Map<String, Object> resultMap = new HashMap<String, Object>();

        List<ShoppingCart> shoppingCartList = shoppingCartService.lambdaQuery().eq(ShoppingCart::getUserId, userId).list();
        resultMap.put("cartList", shoppingCartList);
        serviceResult = ServiceResult.success(resultMap);
        return serviceResult;
    }

    /**
     * 购物车操作
     *
     * @param
     * @return
     */
    @PostMapping("/add2Cart")
    public ServiceResult add2ShoppingCart(@RequestBody SaleInitParamter saleInitParamter) {
        ServiceResult serviceResult;
        String userId = saleInitParamter.getUserId();
        String productId = saleInitParamter.getProductId();
        String skuId = saleInitParamter.getSkuId();
        Integer quantity = saleInitParamter.getQuantity();
        String updateFlag = saleInitParamter.getUpdateFlag();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Integer cartStock;

        System.out.println("saleInitParamter=" + saleInitParamter.toString());
        //先判断商品库存是否满足 满足就增加 不满足就不增加返回前端提示
        ProductSku productSku = productSkuService.lambdaQuery().eq(ProductSku::getProductId, productId).eq(ProductSku::getSkuId, skuId).one();

        //如果库存大于添加商品的数量，允许添加
//        if (productSku.getSkuStock() >= quantity) {
        ShoppingCart cart = new ShoppingCart();

        //查询该物品是否存在购物车内
        ShoppingCart shoppingCart = shoppingCartService.lambdaQuery()
                .eq(ShoppingCart::getUserId, userId)
                .eq(ShoppingCart::getSkuId, skuId)
                .eq(ShoppingCart::getProductId, productId)
                .one();
        if (shoppingCart != null) { //更新购物车数量

            //    添加
            if (updateFlag.equals("0")) {
                cartStock = shoppingCart.getQuantity() + 1;
                //减少
            } else {
                cartStock = shoppingCart.getQuantity() - 1;
            }

            //更新的数量最后大于库存，则提示前端错误
            if (cartStock > productSku.getSkuStock()) {
                serviceResult = ServiceResult.failure("999999", "库存不足");
                //清空该商品的在购物车记录
            } else if (cartStock <= 0) {
                //将该商品从购物车移除
                Map<String, Object> delMap = new HashMap<String, Object>();
                delMap.put("userId", userId);
                delMap.put("productId", productId);
                delMap.put("skuId", skuId);
                shoppingCartService.removeByMap(delMap);
            } else {
                cart.setQuantity(cartStock);
                shoppingCartService.lambdaUpdate()
                        .eq(ShoppingCart::getUserId, userId)
                        .eq(ShoppingCart::getSkuId, skuId)
                        .eq(ShoppingCart::getProductId, productId)
                        .update(cart);
            }

        } else {
            //将该物品sku加入购物车
            BeanUtil.copyProperties(saleInitParamter, cart);
            cart.setCartId(UUID.randomUUID().toString());
            cart.setQuantity(1);
            shoppingCartService.save(cart);
        }
        serviceResult = ServiceResult.success(resultMap);
        return serviceResult;
    }

    /**
     * 清空购物车
     *
     * @param userId
     * @return
     */
    @DeleteMapping("/delete/{userId}")
    public ServiceResult deleteShoppingCartByUserId(@PathVariable String userId) {
        ServiceResult serviceResult;
        Map<String, Object> resultMap = new HashMap<String, Object>();

        Map<String, Object> delMap = new HashMap<String, Object>();
        delMap.put("userId", userId);
        shoppingCartService.removeByMap(delMap);
        serviceResult = ServiceResult.success(resultMap);
        return serviceResult;
    }

    /**
     * 减少商品
     *
     * @param saleInitParamter
     * @return
     */
    @PostMapping("/decreaseProduct")
    public ServiceResult decreaseProduct(@RequestBody SaleInitParamter saleInitParamter) {
        ServiceResult serviceResult =null;
        String userId = saleInitParamter.getUserId();
        String productId = saleInitParamter.getProductId();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        ShoppingCart cart = new ShoppingCart();
        Integer cartStock;

        //查询该物品是否存在购物车内
        List<ShoppingCart> shoppingCarts = shoppingCartService.lambdaQuery()
                .eq(ShoppingCart::getUserId, userId)
                .eq(ShoppingCart::getProductId, productId)
                .list();
        //存在多个商品时，列表处不允许删除，去购物车删除
        if (shoppingCarts.size() > 1) {
            serviceResult = ServiceResult.failure("999999", "多规格商品请前往购物车删除!");
         //只有一种商品时
        } else if (shoppingCarts.size() == 1) {
            cartStock = shoppingCarts.get(0).getQuantity() - 1;
            if (cartStock > 0) {
                cart.setQuantity(cartStock);
                shoppingCartService.lambdaUpdate()
                        .eq(ShoppingCart::getUserId, userId)
                        .eq(ShoppingCart::getProductId, productId)
                        .update(cart);
                serviceResult = ServiceResult.success(resultMap);
            } else {
                Map<String, Object> delMap = new HashMap<String, Object>();
                delMap.put("userId", userId);
                delMap.put("productId", productId);
                shoppingCartService.removeByMap(delMap);
                serviceResult = ServiceResult.success(resultMap);
            }
        //商品不存在购物车中
        } else {
            serviceResult = ServiceResult.failure("999999", "该商品不存在购物车中!");
        }
        return serviceResult;
    }

}
