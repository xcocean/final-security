package top.lingkang.oauth.server.base;

import top.lingkang.oauth.error.OauthClientException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lingkang
 * Created by 2022/1/17
 */
public interface OauthExceptionHandler {
    void oauthClientException(Exception e, HttpServletRequest request, HttpServletResponse response);
}
