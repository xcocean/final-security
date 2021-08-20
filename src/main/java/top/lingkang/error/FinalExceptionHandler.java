package top.lingkang.error;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lingkang
 * @date 2021/8/17 9:43
 * @description
 */
public interface FinalExceptionHandler {
    void notLoginException(NotLoginException e, HttpServletRequest request, HttpServletResponse response) throws Exception;

    void tokenException(TokenException e, HttpServletRequest request, HttpServletResponse response) throws Exception;

    void permissionException(PermissionException e,HttpServletRequest request, HttpServletResponse response)throws Exception;
}
