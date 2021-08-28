package top.lingkang.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import top.lingkang.FinalManager;
import top.lingkang.config.FinalSecurityProperties;
import top.lingkang.error.NotLoginException;
import top.lingkang.error.PermissionException;
import top.lingkang.error.TokenException;
import top.lingkang.security.CheckAuth;
import top.lingkang.security.FinalHttpSecurity;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author lingkang
 * @date 2021/8/10 15:30
 * @description
 */
@Component
public class RequestFilter implements Filter {

    @Autowired
    private FinalSecurityProperties properties;
    @Autowired
    private FinalHttpSecurity finalHttpSecurity;

    private List<String> excludePath = new ArrayList<>();

    private final AntPathMatcher matcher = new AntPathMatcher();

    public void init(FilterConfig filterConfig) {
        excludePath = finalHttpSecurity.getExcludePath();
        if (properties.getExcludePath() == null) {
            excludePath = Arrays.asList(properties.getExcludePath());
        }
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 未配置 checkAuthHashMap 时直接通过
        if (finalHttpSecurity.getCheckAuthHashMap() == null) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String path = request.getServletPath();

        try {
            // 1、排除的路径
            if (excludePath != null) {
                for (String ex : excludePath) {
                    if (matcher.match(ex, path)) {
                        filterChain.doFilter(servletRequest, servletResponse);
                        return;
                    }
                }
            }

            // 1、鉴权逻辑
            for (Map.Entry<String, CheckAuth> map : finalHttpSecurity.getCheckAuthHashMap().entrySet()) {
                if (matcher.match(map.getKey(), path)) {
                    map.getValue().checkAll();
                }
            }

            // 2、校验无异常，通过
            filterChain.doFilter(request, servletResponse);
            return;
        } catch (Exception e) {
            try {
                HttpServletResponse response = (HttpServletResponse) servletResponse;
                handlerException(request, response, e);
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new ServletException(ex);
            }
        }
    }

    // 异常处理
    private void handlerException(HttpServletRequest request, HttpServletResponse response, Exception e) throws Exception {
        if (e instanceof PermissionException) {
            FinalManager.getFinalExceptionHandler().permissionException((PermissionException) e, request, response);
        } else if (e instanceof NotLoginException) {
            FinalManager.getFinalExceptionHandler().notLoginException((NotLoginException) e, request, response);
        } else if (e instanceof TokenException) {
            FinalManager.getFinalExceptionHandler().tokenException((TokenException) e, request, response);
        } else {
            FinalManager.getFinalExceptionHandler().otherException(e, request, response);
        }
    }
}
