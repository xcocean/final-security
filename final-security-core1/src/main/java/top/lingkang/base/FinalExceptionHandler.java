package top.lingkang.base;

import top.lingkang.error.FinalNotLoginException;
import top.lingkang.error.FinalTokenException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lingkang
 * Created by 2022/1/7
 */
public interface FinalExceptionHandler {
    void notLoginException(FinalNotLoginException e, HttpServletRequest request, HttpServletResponse response) throws RuntimeException;

    void tokenException(FinalTokenException e, HttpServletRequest request, HttpServletResponse response) throws RuntimeException;
//
//    void permissionException(FinalPermissionException e, HttpServletRequest request, HttpServletResponse response)throws RuntimeException;
}
