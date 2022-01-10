package top.lingkang.base;

import top.lingkang.error.FinalNotLoginException;
import top.lingkang.error.FinalTokenException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author lingkang
 * Created by 2022/1/7
 */
public interface FinalExceptionHandler {
    void notLoginException(FinalNotLoginException e, ServletRequest servletRequest, ServletResponse servletResponse) throws RuntimeException;

    void tokenException(FinalTokenException e, ServletRequest servletRequest, ServletResponse servletResponse) throws RuntimeException;

    void exception(Exception e, ServletRequest servletRequest, ServletResponse servletResponse) throws RuntimeException;
}
