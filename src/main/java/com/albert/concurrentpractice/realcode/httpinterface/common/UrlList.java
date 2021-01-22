package com.albert.concurrentpractice.realcode.httpinterface.common;


import org.springframework.stereotype.Component;

@Component
public class UrlList {

    //douc接口
//    public static final String DOUC_URL = "http://10.0.2.100/douc/api/v1/sso/directlogin";

    /**
     * 获取token（请求根据用户id获取部门名称需要）
     */
    public static final String DEPT_TOKEN="/xpaas-console-api/api/v1/acApps/thirdAppLogin?tenantId=oppo";


    public static final String GET_DEPT="/openapi/api/v1/user/";

    public static final String GET_INCIDENTLIST="/api/v1/artemis/statistical/incidentlist";

}
