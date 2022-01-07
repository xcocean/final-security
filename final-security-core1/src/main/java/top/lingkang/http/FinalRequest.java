package top.lingkang.http;

import top.lingkang.session.FinalSession;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lingkang
 * @date 2021/8/13 16:06
 * @description
 */
public interface FinalRequest {
    String getHeader(String name);

    String getCookieValue(String name);

    String getParam(String name);

    void setAttribute(String name, Object value);

    FinalSession getFinalSession();

    HttpServletRequest getHttpServletRequest();
}
