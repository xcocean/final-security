package top.lingkang.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.lingkang.base.FinalHttpProperties;
import top.lingkang.error.FinalBaseException;
import top.lingkang.error.FinalNotLoginException;
import top.lingkang.error.FinalPermissionException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lingkang
 * Created by 2022/3/19
 */
@RestControllerAdvice
public class ErrorAopHandler {
    @Autowired
    private FinalHttpProperties properties;
    @ExceptionHandler(FinalBaseException.class)
    public void finalBaseException(FinalBaseException e, HttpServletRequest request, HttpServletResponse response) {
        if (e instanceof FinalPermissionException) {
            properties.getExceptionHandler().permissionException(e, request, response);
        } else if (e instanceof FinalNotLoginException) {
            properties.getExceptionHandler().notLoginException(e, request, response);
        } else {
            properties.getExceptionHandler().exception(e, request, response);
        }
    }
}
