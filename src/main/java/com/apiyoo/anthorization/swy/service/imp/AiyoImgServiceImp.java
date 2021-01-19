package com.apiyoo.anthorization.swy.service.imp;

import com.apiyoo.anthorization.swy.config.URLConstant;
import com.apiyoo.anthorization.swy.entity.AiyoImg;
import com.apiyoo.anthorization.swy.mapper.AiyoImgMapper;
import com.apiyoo.anthorization.swy.service.AiyoImgService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class AiyoImgServiceImp extends ServiceImpl<AiyoImgMapper, AiyoImg> implements AiyoImgService {

    @Resource
    private AiyoImgMapper aiyoImgMapper;

    @Override
    public List<AiyoImg> queryUrlByAid(String aid) {

        return aiyoImgMapper.queryUrlByAid(aid);

    }

    @Override
    public int insertImgPathByAid(String aid, String imgUrl, String imgUrl_press) {
        return aiyoImgMapper.insertImgPathByAid(aid, imgUrl, imgUrl_press);
    }
    @Transactional
    @Override
    public int saveImgpath(String rootPath, String seqNum, MultipartFile file) {


        String timeBar = System.currentTimeMillis() + "";
        //原图路径
        String destFileName = rootPath + File.separator + seqNum + File.separator + timeBar + ".png";
        //压缩路径
        String destFileName_press = rootPath + File.separator + seqNum + File.separator + timeBar + "_press.png";

        System.out.println(destFileName);
        File destFile = new File(destFileName);
        destFile.getParentFile().mkdirs();
        try {
            file.transferTo(destFile);
            String final_picPath = URLConstant.URL_PATH + "/" + "aiyoImg" + "/" + seqNum + "/" + timeBar + ".png";
            String final_picPath_press = URLConstant.URL_PATH + "/" + "aiyoImg" + "/" + seqNum + "/" + timeBar + "_press.png";
            try {
                Thumbnails.of(destFileName).size(120, 120).keepAspectRatio(false).toFile(destFileName_press);
            } catch (IOException e) {
                e.printStackTrace();
                return -1;
            }

            if (this.insertImgPathByAid(seqNum, final_picPath, final_picPath_press) == -1) {
                return -1;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }

        return 0;
    }
}

