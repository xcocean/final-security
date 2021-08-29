package top.lingkang.config;

import org.springframework.web.bind.annotation.ExceptionHandler;
import top.lingkang.FinalManager;
import top.lingkang.error.FinalTokenException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@RestControllerAdvice
public class FinalControllerExceptionHandler {

    @ExceptionHandler(FinalTokenException.class)
    public Object tokenException(FinalTokenException e, HttpServletRequest request, HttpServletResponse response) {
        try {
            FinalManager.getFinalExceptionHandler().tokenException(e, request, response);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

}
