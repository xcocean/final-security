package top.lingkang.error.impl;

import org.springframework.boot.configurationprocessor.json.JSONObject;
import top.lingkang.error.FinalExceptionHandler;
import top.lingkang.error.NotLoginException;
import top.lingkang.error.PermissionException;
import top.lingkang.error.TokenException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author lingkang
 * @date 2021/8/17 9:40
 * @description
 */
public class DefaultFinalExceptionHandler implements FinalExceptionHandler {
    public void notLoginException(NotLoginException e, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setHeader("Content-type", "application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 403);
        jsonObject.put("msg", e.getMessage());
        response.getWriter().print(jsonObject);
    }

    public void tokenException(TokenException e, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setHeader("Content-type", "application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 401);
        jsonObject.put("msg", e.getMessage());
        response.getWriter().print(jsonObject);
    }

    public void permissionException(PermissionException e, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setHeader("Content-type", "application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 401);
        jsonObject.put("msg", e.getMessage());
        response.getWriter().print(jsonObject);
    }

    public void otherException(Exception e, HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Content-type", "text/html; charset=utf-8");
        try {
            response.getWriter().println(e);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
