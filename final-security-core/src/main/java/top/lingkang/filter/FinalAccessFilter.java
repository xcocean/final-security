package top.lingkang.filter;

import top.lingkang.FinalManager;
import top.lingkang.base.FinalAuth;
import top.lingkang.utils.AuthUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author lingkang
 * Created by 2022/1/10
 */
public class FinalAccessFilter implements FinalFilterChain {
    private FinalManager manager;

    public FinalAccessFilter(FinalManager manager) {
        this.manager = manager;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse) {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String path = request.getServletPath();

        for (Map.Entry<String, FinalAuth> entry : manager.getHttpSecurity().getCheckAuths().entrySet()) {
            if (AuthUtils.matcher(entry.getKey(), path)) {
                entry.getValue().check();
            }
        }
    }
}