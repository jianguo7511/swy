package com.apiyoo.anthorization.swy.controller;


import cn.hutool.core.bean.BeanUtil;
import com.apiyoo.anthorization.swy.dto.OrderDetailParamter;
import com.apiyoo.anthorization.swy.dto.OrderParamter;
import com.apiyoo.anthorization.swy.dto.OrderQueryCondition;
import com.apiyoo.anthorization.swy.entity.*;
import com.apiyoo.anthorization.swy.service.*;
import com.apiyoo.anthorization.swy.util.ServiceResult;
import com.apiyoo.anthorization.swy.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author swy
 * @since 2020-03-26
 */
@RestController
@RequestMapping("/order")
public class ApiyooOrderController {

    @Autowired
    ApiyooOrderService apiyooOrderService;

    @Autowired
    ShoppingCartService shoppingCartService;
    @Autowired
    OrderDetailService orderDetailService;

    @Autowired
    AiyoImgService aiyoImgService;

    @Autowired
    ProductSkuService productSkuService;

    @Value("${spring.upload.src}")
    private String rootPath;


    /**
     * 订单列表
     *
     * @param
     * @return
     */
    @PostMapping("/queryOrderDetail")
    public ServiceResult queryOrdeDetail(@Validated @RequestBody OrderDetailParamter orderDetailParamter) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        ServiceResult serviceResult = null;
        String userId = orderDetailParamter.getUserId();
        String orderId = orderDetailParamter.getOrderId();
        //查出订单信息
        ApiyooOrder apiyooOrder = apiyooOrderService.lambdaQuery()
                .eq(ApiyooOrder::getUserId, userId)
                .eq(ApiyooOrder::getOrderId, orderId)
                .one();
        if (apiyooOrder == null) {
            serviceResult = ServiceResult.failure("999999", "订单不存在，请联系管理员");
        }

        OrderVo orderVo = new OrderVo();
        BeanUtil.copyProperties(apiyooOrder, orderVo);
        List<OrderDetail> orderDetailList = orderDetailService.lambdaQuery().eq(OrderDetail::getOrderId, orderId).list();
        orderVo.setOrderDetails(orderDetailList);
        resultMap.put("orderVo", orderVo);
        serviceResult = ServiceResult.success(resultMap);
        return serviceResult;
    }

    /**
     * 订单列表
     *
     * @param
     * @return
     */
    @PostMapping("/queryOrderList")
    public ServiceResult queryOrderListBypName(@RequestBody OrderQueryCondition queryCondition) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String userId = queryCondition.getUserId();
        String ProductName = queryCondition.getProductName();
        List<ApiyooOrder> apiyooOrders = null;
        if (ProductName != null && !"".equals(ProductName)) {
            apiyooOrders = apiyooOrderService.lambdaQuery()
                    .eq(ApiyooOrder::getUserId, userId)
                    .like(ApiyooOrder::getBack1, ProductName)
                    .orderByDesc(ApiyooOrder::getCreatTime)
                    .list();
        } else {
            apiyooOrders = apiyooOrderService.lambdaQuery()
                    .eq(ApiyooOrder::getUserId, userId)
                    .orderByDesc(ApiyooOrder::getCreatTime)
                    .list();
        }

        List<OrderVo> orderVoList = apiyooOrders.stream().map(item -> {
            OrderVo orderVo = new OrderVo();
            BeanUtil.copyProperties(item, orderVo);
            String orderId = item.getOrderId();
            List<OrderDetail> orderDetailList = orderDetailService.lambdaQuery().eq(OrderDetail::getOrderId, orderId).list();
            orderVo.setOrderDetails(orderDetailList);
            return orderVo;

        }).collect(Collectors.toList());


        resultMap.put("apiyooOrders", orderVoList);


        System.out.println(
                "apiyooOrders==" + orderVoList
        );
        ServiceResult serviceResult = ServiceResult.success(resultMap);
        return serviceResult;

    }

    /**
     * 根据分类编码查询查询商品
     *
     * @param userId
     * @return
     */
    @GetMapping("/getPreOrderInfo/{userId}")
    public ServiceResult queryProudctListByCategoryId(@PathVariable String userId) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //查询购物车内物品
        List<ShoppingCart> shoppingCarts = shoppingCartService.lambdaQuery()
                .eq(ShoppingCart::getUserId, userId)
                .list();
        //防止重复加上用户钉钉Id
        String orderId = getOrderString() + userId;
        BigDecimal amount = BigDecimal.ZERO;
        for (ShoppingCart item : shoppingCarts) {
            Integer quantity = item.getQuantity();
            BigDecimal num = new BigDecimal(quantity.toString());
            BigDecimal skuPrice = item.getSkuPrice();
            amount = amount.add(skuPrice.multiply(num));
        }
        resultMap.put("itemList", shoppingCarts);
        resultMap.put("productAmount", amount);
        resultMap.put("orderId", orderId);

        ServiceResult serviceResult = ServiceResult.success(resultMap);
        return serviceResult;
    }

    /**
     * 快递方式生成订单
     *
     * @param request
     * @return serviceResult
     */
    @PostMapping("/createOrderWithKD")
    public ServiceResult createOrderWithKD(HttpServletRequest request) {
        StandardMultipartHttpServletRequest httpServletRequest = (StandardMultipartHttpServletRequest) request;
        MultipartFile file = httpServletRequest.getFile("img.png");
        Map<String, Object> paraMap = new HashMap<String, Object>();

        //下单人Id
        paraMap.put("userId", request.getParameter("userId"));
        //        //订单流水号
        paraMap.put("orderId", request.getParameter("orderId"));
        //收货人
        paraMap.put("receiver", request.getParameter("receiver"));
        //收货人电话
        paraMap.put("receiverTel", request.getParameter("receiverTel"));
        //省
        paraMap.put("province", request.getParameter("province"));
        //市
        paraMap.put("city", request.getParameter("city"));
        //区（县）
        paraMap.put("area", request.getParameter("area"));
        //详细地址
        paraMap.put("addrDetail", request.getParameter("addrDetail"));
        //完整地址
        paraMap.put("fullAddr", request.getParameter("fullAddr"));
        //下单人
        paraMap.put("orderName", request.getParameter("orderName"));
        //运费
        paraMap.put("freight", request.getParameter("freight"));
        //配送方式
        paraMap.put("distribution", request.getParameter("distribution"));
        Map<String, Object> resultMap = new HashMap<String, Object>();
        ServiceResult serviceResult = null;
        boolean ok = true;
        String userId = request.getParameter("userId");
        String orderId = request.getParameter("orderId");
        int flag = aiyoImgService.saveImgpath(rootPath, orderId, file);
        if (flag != -1) {
            ApiyooOrder order = new ApiyooOrder();
            BeanUtil.copyProperties(paraMap, order);

            //查询购物车内物品
            List<ShoppingCart> shoppingCarts = shoppingCartService.lambdaQuery()
                    .eq(ShoppingCart::getUserId, userId)
                    .list();
            //库存是否充足
            boolean stock_not_enough = true;
            BigDecimal amount = BigDecimal.ZERO;
            int number = 0;
            StringBuffer skuNameStr = new StringBuffer();
            for (ShoppingCart item : shoppingCarts) {

                String skuId = item.getSkuId();
                int cart_num = item.getQuantity();

                if (!isStockEnough(skuId, cart_num)) {
                    ok = false;
                    serviceResult = ServiceResult.failure("100000", "您选购的【" + item.getSkuName() + "】库存不足,请重新选择!");
                    break;
                }
                Integer quantity = item.getQuantity();
                BigDecimal num = new BigDecimal(quantity.toString());
                BigDecimal skuPrice = item.getSkuPrice();

                amount = amount.add(skuPrice.multiply(num));
                number += quantity;
                OrderDetail orderDetail = new OrderDetail();

                //用UUID生成订单详情的主键
                orderDetail.setPid(UUID.randomUUID().toString());
                orderDetail.setProductId("");
                orderDetail.setProductName("");
                orderDetail.setSkuId(item.getSkuId());
                orderDetail.setSkuName(item.getSkuName());
                orderDetail.setSkuImg(item.getSkuImg());
                orderDetail.setSalePrice(item.getSkuPrice());
                orderDetail.setQuantity(quantity);
                orderDetail.setAmount(skuPrice.multiply(num));
                orderDetail.setOrderId(orderId);
                skuNameStr.append(item.getSkuName());
                skuNameStr.append(",");
                //创建订单表订单明细关系表(购物车中有几条就创建几条关系)
                orderDetailService.save(orderDetail);
            }
            //去掉最后一个逗号
            String queryName = skuNameStr.substring(0, skuNameStr.length() - 1);
            order.setQuantity(number);
            order.setProductTotalSum(amount);
            BigDecimal freight = new BigDecimal(request.getParameter("freight"));
            order.setPayAmount(freight.add(amount));
            order.setBack1(queryName);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            order.setCreatTime(dtf.format(LocalDateTime.now()));

            if (ok) {
                //1扣减库存 2清空购物车
                if (ReduceStock(userId) && clearCart(userId)) {
                    //创建订单记录
                    apiyooOrderService.save(order);
                    serviceResult = ServiceResult.success(resultMap);
                }

            }
        } else {
            serviceResult = ServiceResult.failure("999999", "打款截图上传失败，请重试!");

        }
        return serviceResult;
    }

    /**
     * 自提方式生成订单
     *
     * @param orderParamter
     * @return serviceResult
     */
    @PostMapping("/createOrderWithZT")
    public ServiceResult createOrderWithZT(@RequestBody OrderParamter orderParamter) {

        String userId = orderParamter.getUserId();
        String orderId = orderParamter.getOrderId();
        int freight = orderParamter.getFreight();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        ServiceResult serviceResult = null;
        boolean ok = true;
        ApiyooOrder order = new ApiyooOrder();

        BeanUtil.copyProperties(orderParamter, order);

        //查询购物车内物品
        List<ShoppingCart> shoppingCarts = shoppingCartService.lambdaQuery()
                .eq(ShoppingCart::getUserId, userId)
                .list();
        //防止重复加上用户钉钉Id
        //String orderId = getOrderString()+userId;
        BigDecimal amount = BigDecimal.ZERO;
        int number = 0;
        StringBuffer skuNameStr = new StringBuffer();
        for (ShoppingCart item : shoppingCarts) {
            String skuId = item.getSkuId();
            int cart_num = item.getQuantity();

            if (!isStockEnough(skuId, cart_num)) {
                ok = false;
                serviceResult = ServiceResult.failure("100000", "您选购的【" + item.getSkuName() + "】库存不足,请重新选择!");
                break;
            }
            Integer quantity = item.getQuantity();
            BigDecimal num = new BigDecimal(quantity.toString());
            BigDecimal skuPrice = item.getSkuPrice();
            amount = amount.add(skuPrice.multiply(num));
            number += quantity;

            OrderDetail orderDetail = new OrderDetail();

            //用UUID生成订单详情的主键
            orderDetail.setPid(UUID.randomUUID().toString());
            orderDetail.setProductId("");
            orderDetail.setProductName("");
            orderDetail.setSkuId(item.getSkuId());
            orderDetail.setSkuName(item.getSkuName());
            orderDetail.setSkuImg(item.getSkuImg());
            orderDetail.setSalePrice(skuPrice);
            orderDetail.setQuantity(quantity);
            orderDetail.setAmount(skuPrice.multiply(num));
            orderDetail.setOrderId(orderId);
            skuNameStr.append(item.getSkuName());
            skuNameStr.append(",");

            //创建订单表订单明细关系表(购物车中有几条就创建几条关系)
            orderDetailService.save(orderDetail);
        }
        String queryName = skuNameStr.substring(0, skuNameStr.length() - 1);
        order.setQuantity(number);
        order.setProductTotalSum(amount);
        BigDecimal freight_b = new BigDecimal(String.valueOf(freight));
        order.setPayAmount(freight_b.add(amount));
        order.setBack1(queryName);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        order.setCreatTime(dtf.format(LocalDateTime.now()));
        if (ok) {
            //扣减库存同时清空购物车
            if (ReduceStock(userId) && clearCart(userId)) {
                //创建订单记录
                apiyooOrderService.save(order);
                serviceResult = ServiceResult.success(resultMap);
            }

        }

        return serviceResult;
    }


    /**
     * 获得订单流水号
     *
     * @return
     */
    private static String getOrderString() {
        String base = "0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 5; ++i) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return System.currentTimeMillis() + sb.toString();

    }

    /**
     * 判断每个sku的库存是否充足
     *
     * @param skuId
     * @param cart_num
     * @return
     */
    private boolean isStockEnough(String skuId, int cart_num) {
        boolean enoughFlag = true;
        ProductSku productSku = productSkuService.lambdaQuery()
                .eq(ProductSku::getSkuId, skuId)
                .one();

        int sku_stock = productSku.getSkuStock();
        //购物车中的sku数量 >库存
        if (cart_num > sku_stock) {
            return false;
        }
        return true;
    }

    /**
     * 清空购物车
     *
     * @param userId
     * @return
     */
    private boolean clearCart(String userId) {
        //清空购物车
        Map<String, Object> delMap = new HashMap<String, Object>();
        delMap.put("userId", userId);
        if (shoppingCartService.removeByMap(delMap)) {
            return true;
        }
        return false;
    }

    /**
     * 扣减库存
     *
     * @param userId
     * @return
     */
    private boolean ReduceStock(String userId) {
        boolean ok = true;

        List<ShoppingCart> shopCarts = shoppingCartService.lambdaQuery()
                .eq(ShoppingCart::getUserId, userId)
                .list();

        for (ShoppingCart cart : shopCarts) {
            int quantity = cart.getQuantity();
            String skuId = cart.getSkuId();
            ProductSku skuNew = new ProductSku();
            ProductSku sku = productSkuService.lambdaQuery()
                    .eq(ProductSku::getSkuId, skuId).one();

            //剩余库存
            int reStock = sku.getSkuStock() - quantity;
            if (reStock < 0) {
                ok = false;
                break;
            }
            skuNew.setSkuStock(reStock);
            //执行扣减
            ok = productSkuService.lambdaUpdate()
                    .eq(ProductSku::getSkuId, skuId)
                    .update(skuNew);
        }

        return ok;

    }
}
