package top.lingkang.config;

import org.springframework.context.annotation.Bean;
import top.lingkang.base.FinalAuth;
import top.lingkang.base.FinalHttpProperties;
import top.lingkang.constants.FinalConstants;
import top.lingkang.constants.FinalSessionKey;
import top.lingkang.error.FinalNotLoginException;
import top.lingkang.error.FinalPermissionException;
import top.lingkang.http.FinalRequestContext;
import top.lingkang.utils.AuthUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

/**
 * @author lingkang
 * Created by 2022/2/11
 */
public class FinalSecurityConfiguration implements Filter {
    private FinalHttpProperties properties = new FinalHttpProperties();
    private HashSet<String> cacheExcludePath = new HashSet<>();
    private HashMap<String, FinalAuth[]> cacheAuths = new HashMap<>();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        FinalRequestContext.setRequest(request);
        try {
            String path = request.getServletPath();
            // 缓存相关
            if (cacheExcludePath.contains(path)) {
                chain.doFilter(servletRequest, servletResponse);
                return;
            } else if (cacheAuths.containsKey(path)) {
                HttpSession session = request.getSession();
                if (session.getAttribute(FinalSessionKey.IS_LOGIN) == null) {
                    throw new FinalNotLoginException(FinalConstants.NOT_LOGIN_MSG);
                }

                FinalAuth[] finalAuths = cacheAuths.get(path);
                for (FinalAuth auth : finalAuths) {
                    auth.check(session);
                }

                chain.doFilter(servletRequest, servletResponse);
                return;
            }

            // 排除
            for (String url : properties.getExcludePath()) {
                if (AuthUtils.matcher(url, path)) {
                    cacheExcludePath.add(path);// 添加缓存
                    chain.doFilter(servletRequest, servletResponse);
                    return;
                }
            }

            // 检查登录
            HttpSession session = request.getSession();
            if (session.getAttribute(FinalSessionKey.IS_LOGIN) == null) {
                throw new FinalNotLoginException(FinalConstants.NOT_LOGIN_MSG);
            }

            // 检查角色
            HashMap<String, FinalAuth> checkAuth = properties.getCheckAuthorize().getAuthorize();
            List<FinalAuth> auths = new ArrayList<>();
            for (Map.Entry<String, FinalAuth> entry : checkAuth.entrySet()) {
                if (AuthUtils.matcher(entry.getKey(), path)) {
                    auths.add(entry.getValue());
                }
            }

            // cache
            cacheAuths.put(path, auths.toArray(new FinalAuth[auths.size()]));

            // 执行检查
            for (FinalAuth auth : auths) {
                auth.check(session);
            }

            //放行
            chain.doFilter(servletRequest, servletResponse);
        } catch (Exception e) {
            if (e instanceof FinalPermissionException) {
                properties.getExceptionHandler().permissionException(e, request, (HttpServletResponse) servletResponse);
            } else if (e instanceof FinalNotLoginException) {
                properties.getExceptionHandler().notLoginException(e, request, (HttpServletResponse) servletResponse);
            } else {
                properties.getExceptionHandler().exception(e, request, (HttpServletResponse) servletResponse);
            }
        } finally {
            FinalRequestContext.removeRequest();
        }
    }

    protected void config(FinalHttpProperties properties) {
        this.properties = properties;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        config(properties);
    }

    @Bean
    public FinalHttpProperties finalHttpProperties() {
        return properties;
    }
}
