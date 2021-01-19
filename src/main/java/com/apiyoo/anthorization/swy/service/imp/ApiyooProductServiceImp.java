package com.apiyoo.anthorization.swy.service.imp;

import com.apiyoo.anthorization.swy.entity.ApiyooProduct;
import com.apiyoo.anthorization.swy.mapper.ApiyooProductMapper;
import com.apiyoo.anthorization.swy.service.ApiyooProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author swy
 * @since 2020-03-25
 */
@Service
public class ApiyooProductServiceImp extends ServiceImpl<ApiyooProductMapper, ApiyooProduct> implements ApiyooProductService {

    @Resource
    private ApiyooProductMapper apiyooProductMapper;


    @Override
    public List<ApiyooProduct> queryProductListByCateId(String productCategory) {
        return apiyooProductMapper.queryProductListByCateId(productCategory);
    }
}
