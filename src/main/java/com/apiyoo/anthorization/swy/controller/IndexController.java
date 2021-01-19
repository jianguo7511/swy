package com.apiyoo.anthorization.swy.controller;


import com.apiyoo.anthorization.swy.config.URLConstant;
import com.apiyoo.anthorization.swy.util.AccessTokenUtil;
import com.apiyoo.anthorization.swy.util.DingTalkClientUtil;
import com.apiyoo.anthorization.swy.util.ServiceResult;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiUserGetRequest;
import com.dingtalk.api.response.OapiUserGetResponse;
import com.taobao.api.ApiException;
import net.coobird.thumbnailator.Thumbnails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 企业内部E应用Quick-Start示例代码 实现了最简单的免密登录（免登）功能
 */
@RestController
public class IndexController {
    private static final Logger bizLogger = LoggerFactory.getLogger(IndexController.class);

    /**
     * 欢迎页面,通过url访问，判断后端服务是否启动
     */
    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public String welcome() {
        return "welcome";
    }

    /**
     * 钉钉用户登录，显示当前登录用户的userId和名称
     */
    @GetMapping("/login/{authCode}")
    public ServiceResult login(@PathVariable String authCode) {
        //返回结果
        Map<String, Object> resultMap = new HashMap<>();
        //获取accessToken,注意正是代码要有异常流处理
        String accessToken = AccessTokenUtil.getToken();
        DingTalkClientUtil dingTalkClientUtil = new DingTalkClientUtil();
        //3.查询得到当前用户的userId
        // 获得到userId之后应用应该处理应用自身的登录会话管理（session）,避免后续的业务交互（前端到应用服务端）每次都要重新获取用户身份，提升用户体验
        String userId = dingTalkClientUtil.getUserId(authCode, accessToken);

        //获取用户信息
        Map<String, String> userMap = dingTalkClientUtil.getUserInfo(userId, accessToken);
        //获取部门信息
        String departId = userMap.get("deptId");
        Map<String, String> departMap = dingTalkClientUtil.getDepartName(departId, accessToken);

        //获取公司名称信息
        Map<String, Object>companyMap =  dingTalkClientUtil.getCompanyList();

        resultMap.putAll(userMap);
        resultMap.putAll(departMap);
        resultMap.putAll(companyMap);

        ServiceResult serviceResult = ServiceResult.success(resultMap);
        return serviceResult;
    }
    /**
     * 获取用户姓名
     *
     * @param accessToken
     * @param userId
     * @return
     */
    private String getUserName(String accessToken, String userId) {
        try {
            DingTalkClient client = new DefaultDingTalkClient(URLConstant.URL_USER_GET);
            OapiUserGetRequest request = new OapiUserGetRequest();
            request.setUserid(userId);
            request.setHttpMethod("GET");
            OapiUserGetResponse response = client.execute(request, accessToken);
            return response.getName();
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 特卖App  登录获取用户信息
     * @param authCode
     * @return
     */
    @GetMapping("/saleLogin/{authCode}")
    public ServiceResult saleLogin(@PathVariable String authCode) {
        //返回结果
        Map<String, Object> resultMap = new HashMap<>();
        //获取accessToken,注意正是代码要有异常流处理
        String accessToken = AccessTokenUtil.getSaleToken();

        DingTalkClientUtil dingTalkClientUtil = new DingTalkClientUtil();
        //3.查询得到当前用户的userId
        // 获得到userId之后应用应该处理应用自身的登录会话管理（session）,避免后续的业务交互（前端到应用服务端）每次都要重新获取用户身份，提升用户体验
        String userId = dingTalkClientUtil.getUserId(authCode, accessToken);

        //获取用户信息
        Map<String, String> userMap = dingTalkClientUtil.getUserInfo(userId, accessToken);
        resultMap.putAll(userMap);

        ServiceResult serviceResult = ServiceResult.success(resultMap);
        return serviceResult;
    }

    public static void main(String[] args) {
        try {
            Thumbnails.of("D:\\dog.png").size(120, 120).keepAspectRatio(true).toFile("D:\\dog_120x120.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


