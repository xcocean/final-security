package top.lingkang.filter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.lingkang.FinalManager;
import top.lingkang.base.FinalAuth;
import top.lingkang.constants.FinalConstants;
import top.lingkang.error.FinalNotLoginException;
import top.lingkang.error.FinalTokenException;
import top.lingkang.utils.AuthUtils;
import top.lingkang.utils.SpringBeanUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author lingkang
 * Created by 2022/1/7
 */
//@Component
public class FinalSecurityFilter implements Filter {
    private static final Log log = LogFactory.getLog(FinalSecurityFilter.class);
    private FinalManager manager;

    public FinalSecurityFilter(FinalManager manager) {
        this.manager = manager;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String path = request.getServletPath();

        // 排除
        for (String url : manager.getHttpSecurity().getExcludePath()) {
            if (AuthUtils.matcher(url, path)) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
        }
        try {
            // 检查令牌时长情况
            String token = manager.getToken();
            long last = manager.getSessionManager().getLastAccessTime(token);
            if (AuthUtils.checkReserveTime(manager.getProperties().getPrepareTime(), manager.getProperties().getMaxValid(), last)) {// 180s 预留时间
                // 不满足预留时间，注销。
                manager.getSessionManager().removeSession(token);
                throw new FinalTokenException(FinalConstants.NOT_EXIST_TOKEN);
            }

            for (Map.Entry<String, FinalAuth> entry : manager.getHttpSecurity().getCheckAuths().entrySet()) {
                if (AuthUtils.matcher(entry.getKey(), path)) {
                    entry.getValue().check();
                }
            }
        } catch (Exception e) {
            if (e instanceof FinalNotLoginException) {// 未登录处理
                manager.getExceptionHandler().notLoginException((FinalNotLoginException) e, request, response);
                return;
            } else if (e instanceof FinalTokenException) {// 无token处理
                manager.getExceptionHandler().tokenException((FinalTokenException) e, request, response);
                return;
            } else {
                log.error(e);
            }
        }

        //放行
        filterChain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {

    }
}
