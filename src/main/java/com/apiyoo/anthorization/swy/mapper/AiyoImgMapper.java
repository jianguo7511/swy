package com.apiyoo.anthorization.swy.mapper;

import com.apiyoo.anthorization.swy.entity.AiyoImg;
import com.apiyoo.anthorization.swy.entity.AuthApply;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AiyoImgMapper extends BaseMapper<AiyoImg> {


    List<AiyoImg> queryUrlByAid(@Param("aid") String aid);    //imgUrl_press

    int insertImgPathByAid(@Param("aid") String aid, @Param("imgUrl") String imgUrl,@Param("imgpress") String imgpress);

}
