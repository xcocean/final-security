package top.lingkang.filter.impl;

import org.springframework.util.AntPathMatcher;
import top.lingkang.filter.FinalFilter;
import top.lingkang.security.CheckAuth;
import top.lingkang.security.FinalHttpSecurity;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * @author lingkang
 * @date 2021/8/21 17:53
 * @description
 */
public class FinalAuthFilter implements FinalFilter {
    private final FinalHttpSecurity finalHttpSecurity;

    private final AntPathMatcher matcher = new AntPathMatcher();

    public FinalAuthFilter(FinalHttpSecurity finalHttpSecurity) {
        this.finalHttpSecurity = finalHttpSecurity;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String path = request.getServletPath();

        // 鉴权逻辑
        if (finalHttpSecurity.getCheckAuthHashMap() != null) {
            for (Map.Entry<String, CheckAuth> map : finalHttpSecurity.getCheckAuthHashMap().entrySet()) {
                if (matcher.match(map.getKey(), path)) {
                    map.getValue().checkAll();
                }
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
