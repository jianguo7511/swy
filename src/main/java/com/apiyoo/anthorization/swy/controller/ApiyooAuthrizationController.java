package com.apiyoo.anthorization.swy.controller;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.apiyoo.anthorization.swy.config.Constant;
import com.apiyoo.anthorization.swy.config.Status;
import com.apiyoo.anthorization.swy.config.URLConstant;
import com.apiyoo.anthorization.swy.entity.AiyoImg;
import com.apiyoo.anthorization.swy.entity.AuthApply;
import com.apiyoo.anthorization.swy.service.AiyoImgService;
import com.apiyoo.anthorization.swy.service.AuthApplyService;
import com.apiyoo.anthorization.swy.util.AccessTokenUtil;
import com.apiyoo.anthorization.swy.util.AiyooUtil;
import com.apiyoo.anthorization.swy.util.ServiceResult;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiProcessinstanceCreateRequest;
import com.dingtalk.api.response.OapiProcessinstanceCreateResponse;
import com.taobao.api.ApiException;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.rmi.CORBA.Util;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
public class ApiyooAuthrizationController {

    @Autowired
    AiyoImgService aiyoImgService;
    @Autowired
    AuthApplyService authApplyService;


    @Value("${spring.upload.src}")
    private String rootPath;


    /**
     * 返回图片给前端
     *
     * @param response
     */
    @RequestMapping("/picture")
    public void upload(HttpServletResponse response, HttpServletRequest request) {
        OutputStream os = null;
        FileInputStream fis = null;
        String URL = request.getParameter("url");
        try {
            os = response.getOutputStream();
            fis = new FileInputStream("E:\\upload\\dingImg\\20200312\\77777\\a1583991141390.png");
            byte[] buffer = new byte[1024];
            while (fis.read(buffer) != -1) {
                os.write(buffer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                os.flush();
                os.close();
                fis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 授权任务提交流程
     *
     * @param request
     * @return
     */
    @PostMapping("/submitAuthApplication")
    public ServiceResult uploadImage(HttpServletRequest request) {

        StandardMultipartHttpServletRequest httpServletRequest = (StandardMultipartHttpServletRequest) request;
        MultipartFile file = httpServletRequest.getFile("img.png");

//        System.out.println();

        //返回Map
        Map<String, Object> resultMap = new HashMap<>();

        //参数Map
        Map<String, Object> paramMap = new HashMap<>();

        ServiceResult serviceResult = null;

        //序列号 不存在则生成
        String seqNum = request.getParameter("seqNum");

        //是否插入授权表 //1插入 0不插入
        String insert = request.getParameter("insert");

        String deptId = request.getParameter("deptId");

        //用户id
        paramMap.put("userId", request.getParameter("userId"));
        //部门id
        paramMap.put("deptId", request.getParameter("deptId"));
        //用户名称
        paramMap.put("userName", request.getParameter("userName"));
        //部门名称
        paramMap.put("departName", request.getParameter("departName"));
        //行业分类
        paramMap.put("classification", request.getParameter("classification"));
        //行业名称
        paramMap.put("industryname", request.getParameter("industryname"));
        //授权公司名称
        paramMap.put("companyname", request.getParameter("companyname"));
        //单位名称
        paramMap.put("orgname", request.getParameter("orgname"));
        //单位地址
        paramMap.put("orgaddress", request.getParameter("orgaddress"));
        //统一社会信用代码
        paramMap.put("creditcode", request.getParameter("creditcode"));
        //公司简介
        paramMap.put("introduction", request.getParameter("introduction"));
        //法人
        paramMap.put("legalperson", request.getParameter("legalperson"));
        //联系人
        paramMap.put("contact", request.getParameter("contact"));
        //联系电话
        paramMap.put("phone", request.getParameter("phone"));
        //备注
        paramMap.put("remarks", request.getParameter("remarks"));

        //如果不存在序列号，则生成
        if ("".equals(seqNum)) {
            seqNum = getSeqNum();
            paramMap.put("seqNum", seqNum);
            resultMap.put("seqNum", seqNum);
        }
        //图片存储
        aiyoImgService.saveImgpath(rootPath, seqNum, file);
        serviceResult = ServiceResult.success(resultMap);
        //发起审批实例 数据入库，  0不插入 1插入
        if ("1".equals(insert)) {
            List<AiyoImg> imgList = aiyoImgService.queryUrlByAid(seqNum);
            paramMap.put("src1", imgList.get(0).getImgurl());
            paramMap.put("src2", imgList.get(1).getImgurl());

            String companyName = request.getParameter("orgname");
            String creditCode = request.getParameter("creditcode");

            //是否可以发起流程
            if (!isApplyAuth(companyName, creditCode)) {
                String errorCode = "999999";
                String errorMessage = "该单位已经发申请";
                serviceResult = ServiceResult.failure(errorCode, errorMessage);
            } else {
                //发起审批实例
                Map<String, Object> map = this.processInstanceCreate(paramMap);
                if ("000000".equals(map.get("errCode"))) {
                    AuthApply authApply = new AuthApply();
                    BeanUtil.copyProperties(paramMap, authApply);
                    // 0等待审批 1已通过 2已拒绝
                    authApply.setApplystatus(0);
                    authApply.setAppstaname(Status.getName(0));
                    authApply.setAid(seqNum);
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    authApply.setSubtime(dtf.format(LocalDateTime.now()));
                    authApply.setApuser(paramMap.get("userName") + "");
                    authApply.setSuborg(paramMap.get("departName") + "");
                    authApply.setProinsid(map.get("processInstanceId") + "");
                    //数据入库
                    boolean flag = authApplyService.save(authApply);

                    if (flag) {
                        resultMap.put("000000", "授权发起成功！");
                    }else{
                        resultMap.put("999999", "授权发起失败！");
                    }
                }
                System.out.println("map审批实例发起=" + map);
            }
        }
        System.out.println("paramMap==" + paramMap);
        return serviceResult;
    }

    /**
     * 根据授权aid查询图片URL
     *
     * @param aid
     * @return
     */
    @PostMapping(value = "/queryImagesByAid")
    private ServiceResult downloadByAid(@RequestBody String aid) {
        Map<String, Object> resultMap = new HashMap<>();
        ServiceResult serviceResult;
        JSONObject jsonObject = JSON.parseObject(aid);
        String param = jsonObject.get("aid").toString();
        List<AiyoImg> imgList = aiyoImgService.queryUrlByAid(param);
        resultMap.put("imgList", imgList);
        serviceResult = ServiceResult.success(resultMap);
        return serviceResult;
    }

    private static synchronized String getSeqNum() {
        String base = "ABCDEFGHJKLMNPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyyMMdd");
        String nowStr = now.format(formatter2);

        for (int i = 0; i < 5; ++i) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        String seqNum = "ay" + nowStr + System.currentTimeMillis() + sb.toString();
        return seqNum;
    }

    /**
     * 获取5位随机数 字符去掉 I和 O
     */
    public synchronized String getFiveRandomString() {
        String base = "ABCDEFGHJKLMNPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < 5; ++i) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 授权申请发起
     *
     * @param paraMap
     * @return
     */
    private static Map processInstanceCreate(Map paraMap) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        try {
            String accessToken = AccessTokenUtil.getToken();
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/processinstance/create");
            OapiProcessinstanceCreateRequest req = new OapiProcessinstanceCreateRequest();
            req.setAgentId(Constant.Ding_Agent_ID);
            req.setProcessCode(Constant.PROCESS_CODE);
            req.setOriginatorUserId(paraMap.get("userId") + "");
            req.setDeptId(Long.parseLong(paraMap.get("deptId") + ""));
            List<OapiProcessinstanceCreateRequest.FormComponentValueVo> list = new ArrayList<OapiProcessinstanceCreateRequest.FormComponentValueVo>();
            OapiProcessinstanceCreateRequest.FormComponentValueVo obj1 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            obj1.setName("行业分类");
            obj1.setValue(paraMap.get("classification") + "");

            OapiProcessinstanceCreateRequest.FormComponentValueVo obj2 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            obj2.setName("行业名称");
            obj2.setValue(paraMap.get("industryname") + "");

            OapiProcessinstanceCreateRequest.FormComponentValueVo obj3 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            obj3.setName("单位名称");
            obj3.setValue(paraMap.get("orgname") + "");

            OapiProcessinstanceCreateRequest.FormComponentValueVo obj4 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            obj4.setName("统一社会信用代码");
            obj4.setValue(paraMap.get("creditcode") + "");

            OapiProcessinstanceCreateRequest.FormComponentValueVo obj5 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            obj5.setName("法人");
            obj5.setValue(paraMap.get("legalperson") + "");

            OapiProcessinstanceCreateRequest.FormComponentValueVo obj6 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            obj6.setName("单位地址");
            obj6.setValue(paraMap.get("orgaddress") + "");

            OapiProcessinstanceCreateRequest.FormComponentValueVo obj7 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            obj7.setName("公司简介");
            obj7.setValue(paraMap.get("introduction") + "");

            OapiProcessinstanceCreateRequest.FormComponentValueVo obj8 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            obj8.setName("联系人");
            obj8.setValue(paraMap.get("contact") + "");

            OapiProcessinstanceCreateRequest.FormComponentValueVo obj9 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            obj9.setName("联系电话");
            obj9.setValue(paraMap.get("phone") + "");

            OapiProcessinstanceCreateRequest.FormComponentValueVo obj10 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            obj9.setName("法人及营业执照");
            String.format("[\"%s\",\"%s\"]", paraMap.get(""), paraMap.get(""));
            obj9.setValue("[\"" + paraMap.get("src1") + "\",\"" + paraMap.get("src2") + "\"]");

            OapiProcessinstanceCreateRequest.FormComponentValueVo obj11 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            obj11.setName("备注");
            obj11.setValue(paraMap.get("remarks") + "");

            list.add(obj1);
            list.add(obj2);
            list.add(obj3);
            list.add(obj4);
            list.add(obj5);
            list.add(obj6);
            list.add(obj7);
            list.add(obj8);
            list.add(obj9);
//            list.add(obj10);
            list.add(obj11);

            req.setFormComponentValues(list);
            OapiProcessinstanceCreateResponse rsp = client.execute(req, accessToken);
            if (rsp.getErrcode() == 0 || "0".equals(rsp.getErrcode())) {
                returnMap.put("errCode", "000000");
                returnMap.put("processInstanceId", rsp.getProcessInstanceId());
            } else {
                returnMap.put("errCode", "999999");
                returnMap.put("Msg", rsp.getBody());
                returnMap.put("errMsg", "审批实例发起失败，请联系管理员！");
            }
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return returnMap;
    }

    /**
     * 是否可以发起申请
     *
     * @param companyName
     * @param creditCode
     * @return flag
     */
    private boolean isApplyAuth(String companyName, String creditCode) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        boolean flag = true;
        //1发起过申请的公司在有效期内不能重新发起
        List<AuthApply> authedList = authApplyService.queryAuthedList(creditCode, companyName);
        //没有发起过授权
        if (authedList.size() > 0) {
            Iterator iterator = authedList.iterator();
            while (iterator.hasNext()) {
                AuthApply authApply = (AuthApply) iterator.next();
                // 处于 4 授权失败 6 授权中 不可发起
                int status = authApply.getApplystatus();
                if (status == 0 || status == 4 || status == 6) {
                    flag = false;
                    break;
                    //授权成功且在有效期内的  不可发起
                } else if (status == 2) {
                    String aptimeblock = authApply.getAptimeblock();
                    long end = Long.parseLong(aptimeblock.substring(aptimeblock.length() - 8, aptimeblock.length()));
                    AiyooUtil util = new AiyooUtil();
                    long now = util.getTime();
                    System.out.println(now);
                    if (now <= end) {
                        flag = false;
                        break;
                    }
                }
            }
        }
        return flag;
    }

}
