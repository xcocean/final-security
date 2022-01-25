package top.lingkang.filter;

import org.springframework.beans.factory.annotation.Autowired;
import top.lingkang.FinalManager;
import top.lingkang.error.FinalPermissionException;
import top.lingkang.error.FinalTokenException;
import top.lingkang.http.FinalContextHolder;
import top.lingkang.http.FinalRequestContext;
import top.lingkang.utils.AuthUtils;
import top.lingkang.utils.CookieUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;

/**
 * @author lingkang
 * Created by 2022/1/7
 */
public class FinalSecurityFilter implements Filter {
    @Autowired
    private FinalManager manager;

    private static HashSet<String> cacheExcludePath = new HashSet<>();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = null;
        try {
            String path = request.getServletPath();
            if (cacheExcludePath.contains(path)) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }

            response = (HttpServletResponse) servletResponse;
            FinalContextHolder.setRequestContext(new FinalRequestContext(request, response));

            // 排除
            for (String url : manager.getProperties().getExcludePath()) {
                if (AuthUtils.matcher(url, path)) {
                    cacheExcludePath.add(path);
                    filterChain.doFilter(servletRequest, servletResponse);
                    return;
                }
            }

            // 过滤链
            for (FinalFilterChain chain : manager.getFilterChains()) {
                chain.doFilter(request, response);
            }

            //放行
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception e) {
            if (e instanceof FinalTokenException) {// 无token处理
                if (manager.getProperties().getUseCookie()) {
                    CookieUtils.tokenToZeroAge(manager.getProperties().getTokenName(), response);
                }
                manager.getExceptionHandler().tokenException(e, request, response);
            } else if (e instanceof FinalPermissionException) {
                manager.getExceptionHandler().permissionException(e, request, response);
            } else {// 其他异常
                manager.getExceptionHandler().exception(e, request, response);
            }
        } finally {
            FinalContextHolder.removeRequestContext();
        }
    }

    public HashSet<String> clearExcludePathCache() {
        HashSet<String> res = new HashSet<>(cacheExcludePath);
        cacheExcludePath.clear();
        return res;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {

    }
}
