package top.lingkang;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.lingkang.config.FinalSecurityConfig;
import top.lingkang.config.FinalSecurityProperties;
import top.lingkang.constants.FinalConstants;
import top.lingkang.error.FinalTokenException;
import top.lingkang.http.impl.FinalRequestSpringMVC;
import top.lingkang.session.FinalSession;
import top.lingkang.session.SessionManager;
import top.lingkang.session.impl.DefaultFinalSession;
import top.lingkang.utils.CookieUtils;

/**
 * @author lingkang
 * Created by 2022/1/7
 */
@ComponentScan("top.lingkang")
@Configuration
@EnableConfigurationProperties(FinalSecurityProperties.class)
public class FinalManager {
    @Autowired
    private FinalSecurityProperties properties;
    @Autowired
    private FinalSecurityConfig config;

    public FinalSession getSession() {
        return getSession(getToken());
    }

    public FinalSession getSession(String token) {
        return config.getSessionManager().getSession(token);
    }

    public boolean isLogin() {
        return isLogin(getToken());
    }

    public boolean isLogin(String token) {
        return config.getSessionManager().existsToken(token);
    }

    public void login(String id) {
        SessionManager sessionManager = config.getSessionManager();
        String token = config.getTokenGenerate().generateToken();
        if (properties.getOnlyOne()) { // 用户只能存在一个会话
            FinalSession session = sessionManager.getSessionById(id);
            if (session != null) {
                sessionManager.removeSession(session.getToken());
            }
        } else {
            // 用户可以存在多个token
        }

        if (properties.getShareToken()) {// 用户存在多个会话，会话的token一致
            FinalSession session = sessionManager.getSessionById(id);
            if (session != null) {
                token = session.getToken();
            }
        }

        // 生成会话
        String refreshToken = config.getTokenGenerate().generateRefreshToken();
        FinalSession session = new DefaultFinalSession(id, token, refreshToken);

        // 添加会话
        ServletRequestAttributes servletRequestAttributes = getServletRequestAttributes();
        sessionManager.addFinalSession(token, session);// 共享会话时，会出现会话覆盖

        // 将token放到当前线程的变量中
        servletRequestAttributes.setAttribute(properties.getTokenName(), token, RequestAttributes.SCOPE_REQUEST);

        if (properties.getUseCookie()) {// 将令牌放到cookie中
            CookieUtils.addToken(
                    servletRequestAttributes.getResponse(),
                    properties.getTokenName(),
                    token,
                    properties.getMaxValid()
            );
        }

        // 会话创建监听
        config.getSessionListener().create(token, id, servletRequestAttributes.getRequest());
    }

    /**
     * 获取token
     */
    public String getToken() {
        ServletRequestAttributes servletRequestAttributes = getServletRequestAttributes();
        FinalRequestSpringMVC requestSpringMVC = new FinalRequestSpringMVC(servletRequestAttributes.getRequest());

        // cookie中获取
        String token = requestSpringMVC.getCookieValue(properties.getTokenName());
        if (!StringUtils.isEmpty(token)) {
            return token;
        }

        // 请求域中获取
        token = requestSpringMVC.getParam(properties.getTokenNameRequest());
        if (!StringUtils.isEmpty(token)) {
            return token;
        }

        // 请求头中获取
        token = requestSpringMVC.getHeader(properties.getTokenNameHeader());
        if (!StringUtils.isEmpty(token)) {
            return token;
        }

        // ThreadLocal 中获取
        Object requestToken = servletRequestAttributes.getAttribute(properties.getTokenName(), RequestAttributes.SCOPE_REQUEST);
        if (requestToken != null) {
            return (String) requestToken;
        }
        throw new FinalTokenException(FinalConstants.NOT_EXIST_TOKEN);
    }

    private static ServletRequestAttributes getServletRequestAttributes() {
        return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    }


    public FinalSecurityConfig getConfig() {
        if (config == null) {
            return new FinalSecurityConfig();
        }
        return config;
    }

    public void setConfig(FinalSecurityConfig config) {
        this.config = config;
    }
}
