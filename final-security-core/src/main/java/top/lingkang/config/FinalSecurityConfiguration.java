package top.lingkang.config;

import top.lingkang.base.FinalAuth;
import top.lingkang.base.FinalHttpProperties;
import top.lingkang.constants.FinalConstants;
import top.lingkang.error.FinalNotLoginException;
import top.lingkang.error.FinalPermissionException;
import top.lingkang.http.FinalContextHolder;
import top.lingkang.utils.AuthUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileNotFoundException;
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
        FinalContextHolder.setRequest(request);
        try {
            String path = request.getServletPath();
            if (cacheExcludePath.contains(path)) {
                chain.doFilter(servletRequest, servletResponse);
                return;
            } else if (cacheAuths.containsKey(path)) {
                HttpSession session = request.getSession();
                if (session.getAttribute("finalLogin") == null) {
                    throw new FinalNotLoginException(FinalConstants.NOT_LOGIN_MSG);
                }

                for (FinalAuth auth : cacheAuths.get(path)) {
                    auth.check(session);
                }

                chain.doFilter(servletRequest, servletResponse);
                return;
            }

            // 排除
            for (String url : properties.getExcludePath()) {
                if (AuthUtils.matcher(url, path)) {
                    cacheExcludePath.add(path);
                    chain.doFilter(servletRequest, servletResponse);
                    return;
                }
            }

            HttpSession session = request.getSession();
            if (session.getAttribute("finalLogin") == null) {
                throw new FinalNotLoginException(FinalConstants.NOT_LOGIN_MSG);
            }

            HashMap<String, FinalAuth> checkAuths = properties.getCheckAuths();
            List<FinalAuth> auths = new ArrayList<>();
            for (Map.Entry<String, FinalAuth> entry : checkAuths.entrySet()) {
                if (AuthUtils.matcher(entry.getKey(), path)) {
                    auths.add(entry.getValue());
                }
            }

            // cache
            cacheAuths.put(path, auths.toArray(new FinalAuth[auths.size()]));

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
            } else if ("NestedServletException".equals(e.getClass().getSimpleName()) &&
                    (e instanceof FileNotFoundException || e instanceof FinalPermissionException)) {
                if (e instanceof FinalPermissionException)
                    properties.getExceptionHandler().permissionException(e, request, (HttpServletResponse) servletResponse);
                else
                    properties.getExceptionHandler().notLoginException(e, request, (HttpServletResponse) servletResponse);
            } else {
                properties.getExceptionHandler().exception(e, request, (HttpServletResponse) servletResponse);
            }
        } finally {
            FinalContextHolder.removeRequest();
        }
    }

    protected void config(FinalHttpProperties properties) {
        this.properties = properties;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        config(properties);
        System.out.println("init ... ");
    }
}
