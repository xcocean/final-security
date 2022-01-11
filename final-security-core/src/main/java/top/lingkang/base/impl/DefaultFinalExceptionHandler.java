package top.lingkang.base.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;
import top.lingkang.base.FinalExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author lingkang
 * Created by 2022/1/7
 */
public class DefaultFinalExceptionHandler implements FinalExceptionHandler {
    private static final Log log = LogFactory.getLog(DefaultFinalExceptionHandler.class);

    @Override
    public void tokenException(Exception e, HttpServletRequest request, HttpServletResponse response) {
        printError(e, request, response, "501");
    }

    @Override
    public void permissionException(Exception e, HttpServletRequest request, HttpServletResponse response) {
        printError(e, request, response, "403");
    }

    @Override
    public void exception(Exception e, HttpServletRequest request, HttpServletResponse response) {
        e.printStackTrace();
        try {
            response.getWriter().println(e.getMessage());
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private void printError(Exception e, HttpServletRequest request, HttpServletResponse response, String code) {
        log.warn(e.getMessage() + "  url=" + request.getServletPath());
        String contentType = request.getContentType();
        if (StringUtils.isEmpty(contentType)) {
            contentType = "text/html; charset=UTF-8";
        }
        response.setContentType(contentType);
        response.setStatus(Integer.valueOf(code));
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
