package com.apiyoo.anthorization.swy.controller;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.apiyoo.anthorization.swy.config.Status;
import com.apiyoo.anthorization.swy.dto.AuthApplyCheckDto;
import com.apiyoo.anthorization.swy.entity.AiyoImg;
import com.apiyoo.anthorization.swy.entity.AuthApply;
import com.apiyoo.anthorization.swy.service.AiyoImgService;
import com.apiyoo.anthorization.swy.service.AuthApplyService;
import com.apiyoo.anthorization.swy.util.EventHandle;
import com.apiyoo.anthorization.swy.util.ServiceResult;
import com.apiyoo.anthorization.swy.vo.AuthApplyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
public class AuthApplyController {

    @Autowired
    AuthApplyService authApplyService;

    @Autowired
    AiyoImgService aiyoImgService;

    /**
     * 授权申请发起
     *
     * @return
     */
    @PostMapping("/submitAuthApply")
    public ServiceResult submitAuthApply(@RequestBody String param) {
        Map map = parseJson2Map(param);
        Map<String, Object> resultMap = new HashMap<>();
        ServiceResult serviceResult;
        AuthApply authApply = new AuthApply();
        BeanUtil.copyProperties(map, authApply);
        System.out.println("authApply==" + authApply);
        // 0等待审批 1已通过 2已拒绝
        authApply.setApplystatus(0);
        System.out.println("getSeqNum()=" + getSeqNum());
        String seqNum = getSeqNum();
        authApply.setAid(seqNum);
        authApplyService.save(authApply);
        resultMap.put("seqNum", seqNum);
        serviceResult = ServiceResult.success(resultMap);
        return serviceResult;
    }

    /**
     * 根据单位名称或者提交人 查询授权列表
     *
     * @param condition
     * @return
     */
    @PostMapping("/getAuthApplyList")
    public Map getAuthApplyList(@RequestBody String condition) {
        Map<String, Object> resultMap = new HashMap<>();
        ServiceResult serviceResult;
        System.out.println("condition==" + condition);
        JSONObject jsonObject = JSON.parseObject(condition);
        String param = jsonObject.get("condition").toString();
        List<AuthApply> queryList = authApplyService.queryApplyList(param, param);
        List<AuthApplyVo> authApplyVoList = queryList.stream().map(p -> {
            AuthApplyVo authApplyVo = new AuthApplyVo();
            authApplyVo.setAid(p.getAid());
            authApplyVo.setApplystatus(p.getApplystatus());
            authApplyVo.setAppstaname(Status.getName(p.getApplystatus()));
            authApplyVo.setSuborg(p.getSuborg());
            authApplyVo.setOrgname(p.getOrgname());
            authApplyVo.setApuser(p.getApuser());
            authApplyVo.setSubtime(p.getSubtime().substring(0, p.getSubtime().length() - 3));
            return authApplyVo;
        }).collect(Collectors.toList());
        resultMap.put("list", authApplyVoList);
        serviceResult = ServiceResult.success(resultMap);
        System.out.println(authApplyVoList);
        return resultMap;
    }

    /**
     * 查询单个授权信息
     *
     * @param aid
     * @return
     */
    @GetMapping("/getAuthApplyOne/{aid}")
    public ServiceResult getAuthApplyOne(@PathVariable String aid) {
        Map<String, Object> resultMap = new HashMap<>();
        AuthApplyVo vo = new AuthApplyVo();
        ServiceResult serviceResult;
        AuthApply auth = authApplyService.queryAuthApplyOne(aid);
        List<AiyoImg> pics = aiyoImgService.queryUrlByAid(aid);
        //图片1
        vo.setSrc1(pics.get(0).getImgurl());
        vo.setSrc1_press(pics.get(0).getImgpress());
        //图片2
        vo.setSrc2(pics.get(1).getImgurl());
        vo.setSrc2_press(pics.get(1).getImgpress());

        auth.setAppstaname(Status.getName(auth.getApplystatus()));
        BeanUtil.copyProperties(auth, vo);
        resultMap.put("dataObject", vo);
        System.out.println("auth=" + auth);
        System.out.println("vo=" + vo);
        serviceResult = ServiceResult.success(resultMap);
        return serviceResult;
    }

    /**
     * 钉钉审批流程回调处理
     *
     * @param authApplyCheckDto
     * @return
     */
    @PutMapping("/updateApplyStatus")
    public ServiceResult update4Apply(@Validated @RequestBody AuthApplyCheckDto authApplyCheckDto) {
        Map<String, Object> resultMap = new HashMap<>();
        ServiceResult serviceResult;

        if (authApplyCheckDto == null) {
            String errorCode = "000004";
            String errorMsg = "审批参数为空，请联系管理员";
            serviceResult = ServiceResult.failure(errorCode, errorMsg);
        } else {
            AuthApply authApply = new AuthApply();
            BeanUtil.copyProperties(authApplyCheckDto, authApply);
            boolean ok = authApplyService.updateById(authApply);
            if (ok) {
                serviceResult = ServiceResult.success(resultMap);
            } else {
                String errorCode = "000003";
                String errorMsg = "数据更新失败，请联系管理员";
                serviceResult = ServiceResult.failure(errorCode, errorMsg);
            }
        }
        return serviceResult;
    }

    @GetMapping("/getTime")
    public static synchronized String getSeqNum() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyyMMdd");
        String nowStr = now.format(formatter2);

        String base = "ABCDEFGHJKLMNPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 5; ++i) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        String seqNum = "ay" + nowStr + System.currentTimeMillis()+sb.toString();
        return seqNum;
    }

    /**
     * 数据转换
     *
     * @param data
     */
    public static Map<String, Object> parseJson2Map(String data) {
        String a[] = data.split("&");
        Map<String, Object> param = new HashMap<>();
        for (int i = 0; i < a.length; i++) {
            String value = "";
            String b[] = a[i].split("=");
            if (b.length != 2) {
                value = "";
            } else {
                try {
                    value = java.net.URLDecoder.decode(b[1], "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            String key = b[0];
            param.put(key, value);
        }
        System.out.println("param=" + param);
        return param;
    }


}
