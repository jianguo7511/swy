package com.apiyoo.anthorization.swy.mapper;
import com.apiyoo.anthorization.swy.entity.Authorization;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorizationMapper extends BaseMapper<Authorization> {
    Authorization querybyAid(String aid);
}
