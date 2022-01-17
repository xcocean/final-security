package top.lingkang.oauth.error.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;
import top.lingkang.oauth.error.OauthExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author lingkang
 * @date 2022/1/17
 */
public class DefaultOauthExceptionHandler implements OauthExceptionHandler {
    private static final Log log = LogFactory.getLog(DefaultOauthExceptionHandler.class);

    @Override
    public void oauthClientException(Exception e, HttpServletRequest request, HttpServletResponse response) {
        printError(e, request, response, 400);
    }

    private void printError(Exception e, HttpServletRequest request, HttpServletResponse response, int code) {
        log.warn(e.getMessage() + "  url=" + request.getServletPath());
        String contentType = request.getContentType();
        if (StringUtils.isEmpty(contentType)) {
            contentType = "text/html; charset=UTF-8";
        }
        response.setContentType(contentType);
        response.setStatus(code);
        try {
            if (contentType.toLowerCase().indexOf("json") != -1) {
                response.getWriter().print("{\"code\":" + code + ",\"msg\":\"" + e.getMessage() + "\"}");
            } else {
                response.getWriter().print(e.getMessage());
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
