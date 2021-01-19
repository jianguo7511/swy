package com.apiyoo.anthorization.swy.mapper;

import com.apiyoo.anthorization.swy.entity.ApiyooProduct;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 商品表 Mapper 接口
 * </p>
 *
 * @author swy
 * @since 2020-03-25
 */
public interface ApiyooProductMapper extends BaseMapper<ApiyooProduct> {

    //根据分类编码查询商品
    List<ApiyooProduct>  queryProductListByCateId( @Param("productCategory") String productCategory);

}
