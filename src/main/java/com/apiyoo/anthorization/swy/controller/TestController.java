package com.apiyoo.anthorization.swy.controller;

import com.apiyoo.anthorization.swy.config.Constant;
import com.apiyoo.anthorization.swy.config.URLConstant;
import com.apiyoo.anthorization.swy.util.AccessTokenUtil;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.*;
import com.dingtalk.api.response.*;
import com.taobao.api.ApiException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class TestController {
    /**
     * 获取43位Base64随机数
     */


    public static void getRandomBase64String() {
        String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < 8; ++i) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }

        System.out.println(sb.toString());
        byte[] aesKey = Base64.decodeBase64(Constant.BASE64 + "=");
    }

    /**
     * 获取16位授权号 字符去掉 I和 O
     */
    public String getAuthCode() {
        String base = "ABCDEFGHJKLMNPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < 16; ++i) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
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
     * 获取注册回调事件
     */
    public static void getCallBack() {
        Map<String, String> jsonMap = new HashMap<String, String>();
        String accessToken = AccessTokenUtil.getToken();
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/call_back/get_call_back");
        OapiCallBackGetCallBackRequest request = new OapiCallBackGetCallBackRequest();
        request.setHttpMethod("GET");
        try {
            OapiCallBackGetCallBackResponse response = client.execute(request, accessToken);
            System.out.println(response.getBody());
            if (response.getErrcode() == 0) {
                jsonMap.put("code", "0000000");
                jsonMap.put("url", response.getUrl());
            } else {
                jsonMap.put("Msg", response.getErrmsg());
            }

        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除注册回调事件
     */
    public static void deleteCallBack() {
        Map<String, String> jsonMap = new HashMap<String, String>();
        String accessToken = AccessTokenUtil.getToken();
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/call_back/delete_call_back");
        OapiCallBackDeleteCallBackRequest request = new OapiCallBackDeleteCallBackRequest();
        request.setHttpMethod("GET");
        try {
            OapiCallBackDeleteCallBackResponse response = client.execute(request, accessToken);
            System.out.println(response.getBody());
            if (response.getErrcode() == 0) {
                jsonMap.put("code", "0000000");
            } else {
                jsonMap.put("Msg", response.getErrmsg());
            }
        } catch (ApiException e) {
            e.printStackTrace();
        }

    }

    /**
     * 删除注册回调事件
     */
    public static void creatCallBackUrl() {
        Map<String, String> jsonMap = new HashMap<String, String>();
        String accessToken = AccessTokenUtil.getToken();
        DingTalkClient client = new DefaultDingTalkClient(URLConstant.REGSTER_URL_PATH);
        OapiCallBackDeleteCallBackRequest request = new OapiCallBackDeleteCallBackRequest();
        request.setHttpMethod("GET");
        try {
            OapiCallBackDeleteCallBackResponse response = client.execute(request, accessToken);
            System.out.println(response.getBody());
            if (response.getErrcode() == 0) {
                jsonMap.put("code", "0000000");
            } else {
                jsonMap.put("Msg", response.getErrmsg());
            }
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }


    /**
     * 将数据进行 MD5 加密，并以16进制字符串格式输出
     * @param data
     * @return
     */
    public static String md5(String data) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return Hex.encodeHexString(md.digest(data.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }


    public static void main(String[] args) {

//        //获取注册回调事件
    // getCallBack();
////
////      //删除注册回调事件
//        deleteCallBack();
//        List<String> items = new ArrayList<String>();
//
//        items.add("A");
//        items.add("B");
//        items.add("C");
//        items.add("D");
//        items.add("E");
//        items.add("G");
//        items.add("J");
//        items.forEach(item->{
//            System.out.println(item);
//        });
//
//
//        items.stream().filter(item->item.equals("J")).forEach(System.out::println);

        String password ="456345634";
        String md5HexStr = md5(password);
        System.out.println("==> MD5 加密前: " + password);
        System.out.println("==> MD5 加密后: " + md5HexStr);

    }

}
