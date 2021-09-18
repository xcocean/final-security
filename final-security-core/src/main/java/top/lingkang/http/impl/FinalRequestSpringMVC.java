package top.lingkang.http.impl;

import top.lingkang.FinalManager;
import top.lingkang.http.FinalRequest;
import top.lingkang.session.FinalSession;
import top.lingkang.utils.TokenUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lingkang
 * @date 2021/8/13 16:10
 * @description
 */
public class FinalRequestSpringMVC implements FinalRequest {

    private HttpServletRequest request;

    public FinalRequestSpringMVC(HttpServletRequest request) {
        this.request = request;
    }

    public FinalRequestSpringMVC() {
    }

    public String getHeader(String name) {
        return request.getHeader(name);
    }

    public String getCookieValue(String name) {
        return TokenUtils.getTokenByCookie(name, request.getCookies());
    }

    public String getParam(String name) {
        return request.getParameter(name);
    }

    public void setAttribute(String name, Object value) {
        request.setAttribute(name, value);
    }

    public FinalSession getFinalSession() {
        return FinalManager.getFinalSession(FinalManager.getToken());
    }

    public HttpServletRequest getHttpServletRequest() {
        return this.request;
    }
}
