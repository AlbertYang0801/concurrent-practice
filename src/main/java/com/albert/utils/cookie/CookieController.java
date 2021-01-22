package com.albert.utils.cookie;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Albert
 * @date 2020/10/30 11:00
 */
public class CookieController {

    private String cookieDomain = "www.baidu.com";

    private void addCookie(String token) {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        //设置指定cookie的有效时间为0，即失效该cookie
        CookieUtil.addCookie(response,cookieDomain,"/","userName",token,0,false);
    }

}
