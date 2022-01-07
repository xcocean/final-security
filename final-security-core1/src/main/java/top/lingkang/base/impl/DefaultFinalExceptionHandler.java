package top.lingkang.base.impl;

import org.springframework.util.StringUtils;
import top.lingkang.base.FinalExceptionHandler;
import top.lingkang.error.FinalNotLoginException;
import top.lingkang.error.FinalTokenException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author lingkang
 * Created by 2022/1/7
 */
public class DefaultFinalExceptionHandler implements FinalExceptionHandler {
    public void notLoginException(FinalNotLoginException e, HttpServletRequest request, HttpServletResponse response) throws RuntimeException {
        printError(e, request, response, "403");
    }

    @Override
    public void tokenException(FinalTokenException e, HttpServletRequest request, HttpServletResponse response) throws RuntimeException {
        printError(e, request, response, "501");
    }

    private void printError(Exception e, HttpServletRequest request, HttpServletResponse response, String code) {
        e.printStackTrace();
        String contentType = request.getContentType();
        if (StringUtils.isEmpty(contentType)) {
            contentType = "text/html; charset=UTF-8";
        }
        response.setContentType(contentType);
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
