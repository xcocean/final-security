package top.lingkang.oauth.error;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface OauthExceptionHandler {

    void oauthClientException(Exception e, HttpServletRequest request, HttpServletResponse response);
}
