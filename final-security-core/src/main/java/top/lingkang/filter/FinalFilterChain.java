package top.lingkang.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lingkang
 * Created by 2022/1/10
 */
public interface FinalFilterChain {
    void doFilter(HttpServletRequest request, HttpServletResponse response);
}
