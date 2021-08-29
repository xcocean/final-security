package top.lingkang.error.impl;

import org.springframework.boot.configurationprocessor.json.JSONObject;
import top.lingkang.error.FinalExceptionHandler;
import top.lingkang.error.FinalNotLoginException;
import top.lingkang.error.FinalPermissionException;
import top.lingkang.error.FinalTokenException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lingkang
 * @date 2021/8/17 9:40
 * @description
 */
public class DefaultFinalExceptionHandler implements FinalExceptionHandler {
    public void notLoginException(FinalNotLoginException e, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setHeader("Content-type", "application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 403);
        jsonObject.put("msg", e.getMessage());
        response.getWriter().print(jsonObject);
    }

    public void tokenException(FinalTokenException e, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setHeader("Content-type", "application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 401);
        jsonObject.put("msg", e.getMessage());
        response.getWriter().print(jsonObject);
    }

    public void permissionException(FinalPermissionException e, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setHeader("Content-type", "application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 401);
        jsonObject.put("msg", e.getMessage());
        response.getWriter().print(jsonObject);
    }
}
