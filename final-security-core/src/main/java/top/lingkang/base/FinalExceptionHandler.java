package top.lingkang.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lingkang
 * Created by 2022/1/7
 */
public interface FinalExceptionHandler {
    void tokenException(Exception e, HttpServletRequest request, HttpServletResponse response);

    void permissionException(Exception e, HttpServletRequest request, HttpServletResponse response);

    void exception(Exception e, HttpServletRequest request, HttpServletResponse response);
}
