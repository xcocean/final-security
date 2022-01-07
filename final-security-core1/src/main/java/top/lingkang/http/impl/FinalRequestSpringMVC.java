package top.lingkang.http.impl;

import top.lingkang.http.FinalRequest;
import top.lingkang.session.FinalSession;
import top.lingkang.utils.CookieUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lingkang
 * Created by 2022/1/7
 */
public class FinalRequestSpringMVC implements FinalRequest {
    private HttpServletRequest request;

    public FinalRequestSpringMVC(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public String getHeader(String name) {
        return request.getHeader(name);
    }

    @Override
    public String getCookieValue(String name) {
        return CookieUtils.getTokenByCookie(name, request.getCookies());
    }

    @Override
    public String getParam(String name) {
        return request.getParameter(name);
    }

    @Override
    public void setAttribute(String name, Object value) {

    }

    @Override
    public FinalSession getFinalSession() {
        return null;
    }

    @Override
    public HttpServletRequest getHttpServletRequest() {
        return null;
    }
}
