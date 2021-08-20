package top.lingkang.filter;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import top.lingkang.FinalManager;
import top.lingkang.config.FinalSecurityConfig;
import top.lingkang.config.FinalSecurityProperties;
import top.lingkang.error.NotLoginException;
import top.lingkang.error.PermissionException;
import top.lingkang.error.TokenException;
import top.lingkang.security.CheckAuth;
import top.lingkang.security.FinalHttpSecurity;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
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
    private ApplicationContext applicationContext;
    @Autowired
    private FinalHttpSecurity finalHttpSecurity;

    private List<String> excludePath = new ArrayList<String>();

    private AntPathMatcher matcher = new AntPathMatcher();

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 排除的路径
        String path = request.getServletPath();
        for (String ex : excludePath) {
            if (matcher.match(ex, path)) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
        }

        try {
            // 鉴权逻辑
            if (finalHttpSecurity.getCheckAuthHashMap() != null) {
                for (Map.Entry<String, CheckAuth> map : finalHttpSecurity.getCheckAuthHashMap().entrySet()) {
                    if (matcher.match(map.getKey(), path)) {
                        map.getValue().checkAll();
                    }
                }
            }

        } catch (Exception e) {
            try {
                handlerException(request, response, e);
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new ServletException(ex);
            }
            return;
        }


        filterChain.doFilter(servletRequest, servletResponse);
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        try {
            FinalSecurityConfig bean = applicationContext.getBean(FinalSecurityConfig.class);
            this.excludePath = bean.getExcludePath();
            return;
        } catch (BeansException e) {
            // 默认排除路径 /login
            List<String> list = Arrays.asList(properties.getExcludePath());
            this.excludePath.addAll(list);
            //this.excludePath.add("/favicon.ico");
        }
    }

    public void destroy() {

    }

    private void handlerException(HttpServletRequest request, HttpServletResponse response, Exception e) throws Exception {
        if (e instanceof PermissionException) {
            FinalManager.getFinalExceptionHandler().permissionException((PermissionException) e, request, response);
        } else if (e instanceof NotLoginException) {
            FinalManager.getFinalExceptionHandler().notLoginException((NotLoginException) e, request, response);
        } else if (e instanceof TokenException) {
            FinalManager.getFinalExceptionHandler().tokenException((TokenException) e, request, response);
        }
    }
}
