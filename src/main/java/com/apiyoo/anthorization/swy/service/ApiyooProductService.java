package com.apiyoo.anthorization.swy.service;

import com.apiyoo.anthorization.swy.entity.ApiyooProduct;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 商品表 服务类
 * </p>
 *
 * @author swy
 * @since 2020-03-25
 */
public interface ApiyooProductService extends IService<ApiyooProduct> {

    //根据分类编码查询商品
    List<ApiyooProduct> queryProductListByCateId(String productCategory);

}
