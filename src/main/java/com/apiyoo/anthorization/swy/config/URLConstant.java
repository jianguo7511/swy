package com.apiyoo.anthorization.swy.config;

public class URLConstant {
    /**
     * 钉钉网关gettoken地址
     */
    public static final String URL_GET_TOKKEN = "https://oapi.dingtalk.com/gettoken";

    /**
     *获取用户在企业内userId的接口URL
     */
    public static final String URL_GET_USER_INFO = "https://oapi.dingtalk.com/user/getuserinfo";

    /**
     *获取用户姓名的接口url
     */
    public static final String URL_USER_GET = "https://oapi.dingtalk.com/user/get";

    /**
     * 内网映射的公网IP
     */
   // public static final String URL_PATH = "http://songwy.vaiwan.com/image";

    /**
     * 内网映射的公网IP
     */
    public static final String URL_PATH = "http://localhost:8080/image";

    /**
     * 生产回调URL
     */
    public static final String REGSTER_URL_PATH = "http://129.211.1.185:8080/CallBack";


    /**
     * 测试回调URL
     */
//    public static final String REGSTER_URL_PATH = "http://songwy.vaiwan.com/CallBack";
}





















