package com.apiyoo.anthorization.swy.util;

import com.apiyoo.anthorization.swy.config.URLConstant;
import com.apiyoo.anthorization.swy.entity.Company;
import com.apiyoo.anthorization.swy.service.AuthApplyService;
import com.apiyoo.anthorization.swy.service.CompanyService;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiDepartmentGetRequest;
import com.dingtalk.api.request.OapiUserGetRequest;
import com.dingtalk.api.request.OapiUserGetuserinfoRequest;
import com.dingtalk.api.response.OapiDepartmentGetResponse;
import com.dingtalk.api.response.OapiUserGetResponse;
import com.dingtalk.api.response.OapiUserGetuserinfoResponse;
import com.taobao.api.ApiException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Component
public class DingTalkClientUtil {

    @Autowired
    private CompanyService companyService;

    public static DingTalkClientUtil dingTalkClientUtil;

    @PostConstruct
    public void init() {
        dingTalkClientUtil = this;
        dingTalkClientUtil.companyService = this.companyService;
    }

    public  String getUserId(String authCode,String accessToken){
        String userId ="";
        //获取用户信息
        DingTalkClient client = new DefaultDingTalkClient(URLConstant.URL_GET_USER_INFO);
        OapiUserGetuserinfoRequest request = new OapiUserGetuserinfoRequest();
        request.setCode(authCode);
        request.setHttpMethod("GET");
        OapiUserGetuserinfoResponse response;
        try {
            response = client.execute(request, accessToken);
             userId = response.getUserid();
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }
        return userId;
    }

    /**
     * 获取用户信息
     */
    public Map<String, String> getUserInfo(String userId, String accessToken) {
        Map<String, String> resultMap = new HashMap<String, String>();
        DingTalkClient userClient = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/get");
        OapiUserGetRequest userRequest = new OapiUserGetRequest();
        userRequest.setUserid(userId);
        userRequest.setHttpMethod("GET");
        try {
            OapiUserGetResponse response4UserDetail = userClient.execute(userRequest, accessToken);
            System.out.println("个人信息===" + response4UserDetail.getBody());
            resultMap.put("userId", userId);
            resultMap.put("userName", response4UserDetail.getName());
            resultMap.put("deptId", (response4UserDetail.getDepartment()).get(0).toString());
            System.out.println("department=" + response4UserDetail.getDepartment());
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return resultMap;

    }

    /**
     * 获取部门名称
     * @param deptId
     * @param accessToken
     * @return
     */
    public Map<String, String> getDepartName(String deptId, String accessToken) {
        Map<String, String> resultMap = new HashMap<String, String>();
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/get");
        OapiDepartmentGetRequest request = new OapiDepartmentGetRequest();
        request.setId(deptId);
        request.setHttpMethod("GET");
        OapiDepartmentGetResponse response = null;
        try {
            response = client.execute(request, accessToken);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        String  departName = response.getName();
        resultMap.put("departName", response.getName());
        System.out.println("departName="+departName);

        return resultMap;
    }

    /**
     * 获取授权公司名称
     * @return
     */
    public Map<String, Object> getCompanyList( ) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        List<String> companyList = dingTalkClientUtil.companyService.queryCompanyList();
        resultMap.put("company",companyList);
        System.out.println("companyList=="+resultMap);
        return resultMap;
    }

}
