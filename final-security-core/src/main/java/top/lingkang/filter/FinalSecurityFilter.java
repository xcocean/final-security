package top.lingkang.filter;

import top.lingkang.FinalManager;
import top.lingkang.error.FinalNotLoginException;
import top.lingkang.error.FinalTokenException;
import top.lingkang.utils.AuthUtils;
import top.lingkang.utils.CookieUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author lingkang
 * Created by 2022/1/7
 */
public class FinalSecurityFilter implements Filter {
    private FinalManager manager;

    public FinalSecurityFilter(FinalManager manager) {
        this.manager = manager;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String path = request.getServletPath();
        // 排除
        for (String url : manager.getProperties().getExcludePath()) {
            if (AuthUtils.matcher(url, path)) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
        }

        try {
            for (FinalFilterChain chain : manager.getFilterChains()) {// 过滤链
                chain.doFilter(servletRequest, servletResponse);
            }
        } catch (Exception e) {
            if (e instanceof FinalNotLoginException) {// 未登录处理
                manager.getExceptionHandler().notLoginException((FinalNotLoginException) e, servletRequest, servletResponse);
            } else if (e instanceof FinalTokenException) {// 无token处理
                if (manager.getProperties().getUseCookie()) {
                    CookieUtils.tokenToZeroAge(manager.getProperties().getTokenName(), (HttpServletResponse) servletResponse);
                }
                manager.getExceptionHandler().tokenException((FinalTokenException) e, servletRequest, servletResponse);
            } else {// 其他异常
                manager.getExceptionHandler().exception(e, servletRequest, servletResponse);
            }
            return;
        }

        //放行
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {

    }
}
