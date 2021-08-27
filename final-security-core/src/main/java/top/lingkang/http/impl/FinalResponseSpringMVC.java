package top.lingkang.http.impl;

import top.lingkang.http.FinalResponse;
import top.lingkang.utils.TokenUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lingkang
 * @date 2021/8/13 17:17
 * @description
 */
public class FinalResponseSpringMVC implements FinalResponse {
    private HttpServletResponse response;

    public FinalResponseSpringMVC() {
    }

    public FinalResponseSpringMVC(HttpServletResponse response) {
        this.response = response;
    }

    public void addCookie(Cookie cookie) {
        response.addCookie(cookie);
    }

    public void addToken(String name, String value, int expiry) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        if (expiry==-1){
            addCookie(cookie);
        }else{
            cookie.setMaxAge(expiry);
            addCookie(cookie);
        }
    }
}
