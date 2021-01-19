package com.apiyoo.anthorization.swy.commandLineRunner;

import com.apiyoo.anthorization.swy.config.Constant;
import com.apiyoo.anthorization.swy.config.URLConstant;
import com.apiyoo.anthorization.swy.util.AccessTokenUtil;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiCallBackRegisterCallBackRequest;
import com.dingtalk.api.response.OapiCallBackRegisterCallBackResponse;
import com.taobao.api.ApiException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Order(1)
public class RegisterCallBackRunner implements CommandLineRunner {


    @Override
    public void run(String... args) throws Exception {
        System.out.println("初始化注册审批实例回调开始...");
        String accessToken = AccessTokenUtil.getToken();
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/call_back/register_call_back");
        OapiCallBackRegisterCallBackRequest request = new OapiCallBackRegisterCallBackRequest();
        request.setUrl(URLConstant.REGSTER_URL_PATH);
        request.setAesKey(Constant.BASE64);
        request.setToken(Constant.TOKEN);
        request.setCallBackTag(Arrays.asList("bpms_task_change", "bpms_instance_change"));
        try {

            OapiCallBackRegisterCallBackResponse response = client.execute(request, accessToken);
            System.out.println(response.getBody());

        } catch (ApiException e) {
            e.printStackTrace();
        }
        System.out.println("初始化审批实例回调注册结束...");
    }
}
