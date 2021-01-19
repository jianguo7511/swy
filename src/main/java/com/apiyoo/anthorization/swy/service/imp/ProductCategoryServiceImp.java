package com.apiyoo.anthorization.swy.service.imp;

import com.apiyoo.anthorization.swy.entity.ProductCategory;
import com.apiyoo.anthorization.swy.mapper.ProductCategoryMapper;
import com.apiyoo.anthorization.swy.service.ProductCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品分类表 服务实现类
 * </p>
 *
 * @author swy
 * @since 2020-04-02
 */
@Service
public class ProductCategoryServiceImp extends ServiceImpl<ProductCategoryMapper, ProductCategory> implements ProductCategoryService {

}
