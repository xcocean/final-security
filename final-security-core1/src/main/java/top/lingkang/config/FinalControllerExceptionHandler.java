package top.lingkang.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.lingkang.error.FinalBaseException;
import top.lingkang.error.FinalTokenException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lingkang
 * @date 2022/1/8
 */
@RestControllerAdvice
public class FinalControllerExceptionHandler {
    @Autowired
    private FinalSecurityConfig securityConfig;

    @ExceptionHandler(FinalBaseException.class)
    public Object FinalBaseException(FinalBaseException e, HttpServletRequest request, HttpServletResponse response) {
        if (e instanceof FinalTokenException)
            securityConfig.getExceptionHandler().tokenException((FinalTokenException) e, request, response);
        return null;
    }
}
