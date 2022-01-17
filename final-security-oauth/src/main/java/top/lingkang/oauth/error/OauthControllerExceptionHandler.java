package top.lingkang.oauth.error;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lingkang
 * @date 2022/1/17
 */
@RestControllerAdvice
public class OauthControllerExceptionHandler {
    @Autowired
    private OauthExceptionHandler exceptionHandler;

    @ExceptionHandler(OauthBaseException.class)
    public Object oauthBaseException(Exception e, HttpServletRequest request, HttpServletResponse response) {
        if (e instanceof OauthClientException) {
            exceptionHandler.oauthClientException(e, request, response);
        }
        return null;
    }
}
