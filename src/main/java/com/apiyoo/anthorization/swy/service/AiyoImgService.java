package com.apiyoo.anthorization.swy.service;

import com.apiyoo.anthorization.swy.entity.AiyoImg;
import com.apiyoo.anthorization.swy.entity.AuthApply;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AiyoImgService extends IService<AiyoImg> {

    /**
     * 查询图片链接
     *
     * @return
     */
    List<AiyoImg> queryUrlByAid(String aid);


    int insertImgPathByAid(String aid, String imgUrl,String imgUrl_press);


     int  saveImgpath (String rootPath, String seqNum, MultipartFile file);





}
