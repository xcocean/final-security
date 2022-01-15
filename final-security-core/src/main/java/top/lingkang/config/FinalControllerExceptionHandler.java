package top.lingkang.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.lingkang.FinalManager;
import top.lingkang.error.FinalBaseException;
import top.lingkang.error.FinalPermissionException;
import top.lingkang.error.FinalTokenException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lingkang
 * @date 2022/1/8
 */
@RestControllerAdvice
public class FinalControllerExceptionHandler {
    private static final Log log = LogFactory.getLog(FinalControllerExceptionHandler.class);
    @Autowired
    private FinalManager manager;

    @ExceptionHandler(FinalBaseException.class)
    public Object FinalBaseException(FinalBaseException e, HttpServletRequest request, HttpServletResponse response) {
        // log.error("controller exist final-security error");
        if (e instanceof FinalTokenException) {
            manager.getExceptionHandler().tokenException(e, request, response);
        }else if (e instanceof FinalPermissionException){
            manager.getExceptionHandler().permissionException(e, request, response);
        }
        return null;
    }
}
