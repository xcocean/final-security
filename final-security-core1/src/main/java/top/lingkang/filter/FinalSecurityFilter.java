package top.lingkang.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import top.lingkang.FinalManager;
import top.lingkang.config.FinalSecurityProperties;
import top.lingkang.constants.FinalConstants;
import top.lingkang.error.FinalNotLoginException;
import top.lingkang.error.FinalTokenException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author lingkang
 * Created by 2022/1/7
 */
@Component
public class FinalSecurityFilter implements Filter {
    private final AntPathMatcher matcher = new AntPathMatcher();
    @Autowired
    private FinalManager manager;

    @Autowired
    private FinalSecurityProperties properties;


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String path = request.getServletPath();

        // 排除
        for (String url : properties.getExcludePath()) {
            if (matcher.match(url, path)) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
        }
        try {
            throw new FinalNotLoginException(FinalConstants.NOT_LOGIN_EXCEPTION);
        } catch (Exception e) {
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            if (e instanceof FinalNotLoginException) {// 未登录处理
                manager.getConfig().getExceptionHandler().notLoginException((FinalNotLoginException) e, request, response);
                return;
            } else if (e instanceof FinalTokenException) {// 无token处理
                manager.getConfig().getExceptionHandler().tokenException((FinalTokenException) e, request, response);
                return;
            }
            e.printStackTrace();
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
