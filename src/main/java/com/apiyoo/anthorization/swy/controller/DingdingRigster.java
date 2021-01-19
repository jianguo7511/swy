package com.apiyoo.anthorization.swy.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.apiyoo.anthorization.swy.config.ApproType;
import com.apiyoo.anthorization.swy.config.Constant;
import com.apiyoo.anthorization.swy.dto.CallbackParamter;
import com.apiyoo.anthorization.swy.entity.AuthApply;
import com.apiyoo.anthorization.swy.service.AuthApplyService;
import com.apiyoo.anthorization.swy.util.AccessTokenUtil;
import com.apiyoo.anthorization.swy.util.EventHandle;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiCallBackRegisterCallBackRequest;
import com.dingtalk.api.response.OapiCallBackRegisterCallBackResponse;
import com.dingtalk.oapi.lib.aes.DingTalkEncryptException;
import com.dingtalk.oapi.lib.aes.DingTalkEncryptor;
import com.taobao.api.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
public class DingdingRigster {

    @Autowired
    AuthApplyService authApplyService;

    @PostMapping("/CallBack")
    public void ReturnDingding(HttpServletRequest request, HttpServletResponse response, @RequestBody CallbackParamter p) {
        String plaintext = "";
        Map<String, String> paramMap = new HashMap<String, String>();
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");

        paramMap.put("signature", signature);
        paramMap.put("timestamp", timestamp);
        paramMap.put("nonce", nonce);

        DingTalkEncryptor dingTalkEncryptor = null;
        try {
            dingTalkEncryptor = new DingTalkEncryptor(Constant.TOKEN, Constant.BASE64, Constant.CORPID);
        } catch (DingTalkEncryptException e) {
            e.printStackTrace();
        }
        try {
            //消息明文
            plaintext = dingTalkEncryptor.getDecryptMsg(signature, timestamp, nonce, p.getEncrypt());

            JSONObject jsonObject = JSON.parseObject(plaintext);
            //所有的审批回调都走这里，这里打印该审批模板的日志
            if (Constant.PROCESS_CODE.equals(jsonObject.get("processCode"))) {
                System.out.println("回调结果打印===" + jsonObject.toJSONString());
            }
            //事件类型
            String eventType = jsonObject.get("EventType").toString();
            EventHandle eventHandle = new EventHandle();
            switch (eventType) {
                //注册回调接口
                case "check_url":
                    eventHandle.checkUrl(dingTalkEncryptor, response, timestamp, nonce);
                    break;
                //审批中间状态改变
                case "bpms_task_change":
                    eventHandle.bpmsTaskChange(jsonObject);
                    //审批模板ID
                    break;
                //审批最终状态改变
                case "bpms_instance_change":
                    eventHandle.bpmsInstanceChange(jsonObject);
                    break;
                default:
                    //
                    break;
            }

        } catch (DingTalkEncryptException e) {
            e.printStackTrace();
        }
    }

}
