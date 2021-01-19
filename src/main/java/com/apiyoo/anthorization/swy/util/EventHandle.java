package com.apiyoo.anthorization.swy.util;

import com.alibaba.fastjson.JSONObject;
import com.apiyoo.anthorization.swy.config.ApproType;
import com.apiyoo.anthorization.swy.config.Constant;
import com.apiyoo.anthorization.swy.config.Status;
import com.apiyoo.anthorization.swy.entity.AuthApply;
import com.apiyoo.anthorization.swy.service.AuthApplyService;
import com.dingtalk.oapi.lib.aes.DingTalkEncryptException;
import com.dingtalk.oapi.lib.aes.DingTalkEncryptor;
import org.apache.http.auth.AUTH;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * 钉钉回调处理类
 */
@Component
public class EventHandle {


    @Autowired
    private AuthApplyService authApplyService;

    public static EventHandle eventHandle;

    @PostConstruct
    public void init() {
        eventHandle = this;
        eventHandle.authApplyService = this.authApplyService;
    }


    /**
     * 测试回调方法
     *
     * @param dingTalkEncryptor
     * @param response
     * @param timestamp
     * @param nonce
     */
    public void checkUrl(DingTalkEncryptor dingTalkEncryptor, HttpServletResponse response, String timestamp, String nonce) {
        Map<String, String> jsonMap = null;
        try {
            long timestamp_l = Long.parseLong(timestamp);
            jsonMap = dingTalkEncryptor.getEncryptedMap("success", timestamp_l, nonce);
        } catch (DingTalkEncryptException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        JSONObject json = new JSONObject();
        json.putAll(jsonMap);
        try {
            response.getWriter().append(json.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 审批事件回调中间结果处理
     */
    public void bpmsTaskChange(JSONObject jsonObject) {
        //审批模板ID
        String processCode = jsonObject.get("processCode").toString();
        //审批模板实例ID
        String processInstanceId = jsonObject.get("processInstanceId").toString();
        //类型   finish完成   terminal 中止
        String type = jsonObject.get("type").toString();

        int applystatus = 0;
        String remark = "";
        String appstaname = "";
        if (Constant.PROCESS_CODE.equals(processCode) && (ApproType.getName(0)).equals(type)) {
            //正常结束时result为agree，拒绝时result为refuse，审批终止时没这个值
            String result = jsonObject.get("result") == null ? "" : jsonObject.get("result").toString();
            //操作理由
            remark = jsonObject.get("remark") == null ? "" : jsonObject.get("remark") + "";
            if ("refuse".equals(result)) {
                applystatus = 4;
            } else if ("agree".equals(result)) {
                applystatus = 6;
            }
            appstaname = Status.getName(applystatus);
            //授权成功或者失败，都是有审批实例的回调结果确定
            AuthApply authApply = eventHandle.authApplyService.queryAuthApplyByProinsid(processInstanceId);
            if (authApply == null) {
                System.out.println("授权任务不存在，请联系管理员！");
            } else {
                int applyStatus = authApply.getApplystatus();
                if (0 == authApply.getApplystatus()) {
                    eventHandle.authApplyService.updateAuthApplyByPid(applystatus, remark, appstaname, processInstanceId);
                }
            }
        }
    }

    /**
     * 审批事件回调结果处理
     *
     * @param jsonObject
     */
    public void bpmsInstanceChange(JSONObject jsonObject) {
        //审批模板ID
        String processCode = jsonObject.get("processCode").toString();
        //审批模板实例ID
        String processInstanceId = jsonObject.get("processInstanceId").toString();
        //类型   finish完成   terminal 中止
        String type = jsonObject.get("type").toString();
        int applystatus = 0;
        String remark = "";
        String appstaname = "";
        AiyooUtil aiyooUtil = new AiyooUtil();
        AuthApply authApply = new AuthApply();
        if (Constant.PROCESS_CODE.equals(processCode) && (ApproType.getName(0)).equals(type)) {
            //正常结束时result为agree，拒绝时result为refuse，审批终止时没这个值
            String result = jsonObject.get("result") == null ? "" : jsonObject.get("result").toString();
            //操作理由
            remark = jsonObject.get("remark") == null ? "" : jsonObject.get("remark") + "";
            if ("refuse".equals(result)) {
                applystatus = 4;
            } else if ("agree".equals(result)) {
                applystatus = 2;
                Map<String, String> paramMap = aiyooUtil.getAuthInfo();
                String aptimeblock = paramMap.get("aptimeblock");
                String authcode = paramMap.get("authcode");
                String authtime = paramMap.get("authtime");
                authApply.setAuthtime(authtime);
                authApply.setAuthcode(authcode);
                authApply.setAptimeblock(aptimeblock);
            }
            appstaname = Status.getName(applystatus);
            AuthApply authApply1 = eventHandle.authApplyService.queryAuthApplyByProinsid(processInstanceId);
            System.out.println("authApply1=====" + authApply1);
            authApply.setApplystatus(applystatus);
            authApply.setRemarks(remark);
            authApply.setAppstaname(appstaname);
            authApply.setProinsid(processInstanceId);
            //更新审批状态 0等待审批 2已通过 4已拒绝 6审批中
            eventHandle.authApplyService.updateAuthApplySuccessByPid(authApply);
        }

    }
}
