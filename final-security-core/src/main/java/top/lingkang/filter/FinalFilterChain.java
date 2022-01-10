package top.lingkang.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author lingkang
 * Created by 2022/1/10
 */
public interface FinalFilterChain {
    void doFilter(ServletRequest servletRequest, ServletResponse servletResponse);
}
