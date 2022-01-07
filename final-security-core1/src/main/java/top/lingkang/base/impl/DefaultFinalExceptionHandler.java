package top.lingkang.base.impl;

import top.lingkang.base.FinalExceptionHandler;
import top.lingkang.error.FinalNotLoginException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author lingkang
 * Created by 2022/1/7
 */
public class DefaultFinalExceptionHandler implements FinalExceptionHandler {
    public void notLoginException(FinalNotLoginException e, HttpServletRequest request, HttpServletResponse response) throws RuntimeException {
        response.setHeader("Content-type", "application/json; charset=utf-8");
        try {
            response.getWriter().print("{\"code\":403,\"message\":\"" + e.getMessage() + "\"}");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
