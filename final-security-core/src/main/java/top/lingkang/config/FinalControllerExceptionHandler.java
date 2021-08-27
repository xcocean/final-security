package top.lingkang.config;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.lingkang.FinalManager;
import top.lingkang.error.TokenException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class FinalControllerExceptionHandler {

    @ExceptionHandler(TokenException.class)
    public Object tokenException(TokenException e, HttpServletRequest request, HttpServletResponse response) {
        try {
            FinalManager.getFinalExceptionHandler().tokenException(e, request, response);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

}
