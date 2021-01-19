package com.apiyoo.anthorization.swy.service.imp;

import com.apiyoo.anthorization.swy.entity.Package;
import com.apiyoo.anthorization.swy.mapper.PackageMapper;
import com.apiyoo.anthorization.swy.service.PackageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 包裹表 服务实现类
 * </p>
 *
 * @author swy
 * @since 2020-03-25
 */
@Service
public class PackageServiceImp extends ServiceImpl<PackageMapper, Package> implements PackageService {

}
