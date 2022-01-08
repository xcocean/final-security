package top.lingkang;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.lingkang.base.FinalExceptionHandler;
import top.lingkang.base.FinalHttpSecurity;
import top.lingkang.base.FinalSessionListener;
import top.lingkang.base.FinalTokenGenerate;
import top.lingkang.base.impl.DefaultFinalExceptionHandler;
import top.lingkang.base.impl.DefaultFinalHttpSecurity;
import top.lingkang.base.impl.DefaultFinalSessionListener;
import top.lingkang.base.impl.DefaultFinalTokenGenerate;
import top.lingkang.config.FinalSecurityConfiguration;
import top.lingkang.config.FinalSecurityProperties;
import top.lingkang.constants.FinalConstants;
import top.lingkang.error.FinalTokenException;
import top.lingkang.http.impl.FinalRequestSpringMVC;
import top.lingkang.session.FinalSession;
import top.lingkang.session.SessionManager;
import top.lingkang.session.impl.DefaultFinalSession;
import top.lingkang.session.impl.DefaultFinalSessionManager;
import top.lingkang.utils.CookieUtils;
import top.lingkang.utils.SpringBeanUtils;

import java.util.Arrays;

/**
 * @author lingkang
 * Created by 2022/1/7
 */
@ComponentScan("top.lingkang")
@Configuration
@EnableConfigurationProperties(FinalSecurityProperties.class)
public class FinalManager implements ApplicationRunner {

    @Autowired
    private FinalSecurityProperties securityProperties;

    private FinalExceptionHandler exceptionHandler;
    private FinalTokenGenerate tokenGenerate;
    private SessionManager sessionManager;
    private FinalSessionListener sessionListener;
    private FinalHttpSecurity httpSecurity;
    private FinalSecurityProperties properties;

    private FinalSecurityConfiguration configuration;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if ((exceptionHandler = SpringBeanUtils.getBean(FinalExceptionHandler.class)) == null) {
            exceptionHandler = new DefaultFinalExceptionHandler();
        }
        if ((tokenGenerate = SpringBeanUtils.getBean(FinalTokenGenerate.class)) == null) {
            tokenGenerate = new DefaultFinalTokenGenerate();
        }
        if ((sessionManager = SpringBeanUtils.getBean(SessionManager.class)) == null) {
            sessionManager = new DefaultFinalSessionManager();
        }
        if ((sessionListener = SpringBeanUtils.getBean(FinalSessionListener.class)) == null) {
            sessionListener = new DefaultFinalSessionListener();
        }
        if ((httpSecurity = SpringBeanUtils.getBean(FinalHttpSecurity.class)) == null) {
            httpSecurity = new DefaultFinalHttpSecurity();
            httpSecurity.setExcludePath(Arrays.asList(securityProperties.getExcludePath()));
        }
        if ((configuration = SpringBeanUtils.getBean(FinalSecurityConfiguration.class)) == null) {
            configuration = new FinalSecurityConfiguration();
            configuration.setProperties(securityProperties);
        }
        properties = configuration.getProperties();
    }

    public FinalSession getSession() {
        return getSession(getToken());
    }

    public FinalSession getSession(String token) {
        return sessionManager.getSession(token);
    }

    public boolean isLogin() {
        return isLogin(getToken());
    }

    public boolean isLogin(String token) {
        return sessionManager.existsToken(token);
    }

    public void login(String id) {
        if (properties.getOnlyOne()) { // 用户只能存在一个会话
            FinalSession session = sessionManager.getSessionById(id);
            if (session != null) {
                sessionManager.removeSession(session.getToken());
            }
        } else {
            // 用户可以存在多个token
        }

        String refreshToken = null;
        String token = null;
        try {
            FinalSession session = sessionManager.getSession(getToken());
            if (session != null) {
                token = session.getToken();
                refreshToken = session.getRefreshToken();
                sessionManager.removeSession(session.getToken());
            }
        } catch (Exception e) {
        }

        // 生成会话，如果存在token则覆盖会话
        if (token == null)
            token = tokenGenerate.generateToken();
        if (refreshToken == null)
            refreshToken = tokenGenerate.generateRefreshToken();
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
        sessionListener.create(token, id, servletRequestAttributes.getRequest());
    }

    /**
     * 获取token
     */
    public String getToken() {
        // ThreadLocal 中获取
        ServletRequestAttributes servletRequestAttributes = getServletRequestAttributes();
        Object requestToken = servletRequestAttributes.getAttribute(properties.getTokenName(), RequestAttributes.SCOPE_REQUEST);
        if (requestToken != null) {
            return (String) requestToken;
        }

        FinalRequestSpringMVC requestSpringMVC = new FinalRequestSpringMVC(servletRequestAttributes.getRequest());

        // 请求头中获取
        String token = requestSpringMVC.getHeader(properties.getTokenNameHeader());
        if (!StringUtils.isEmpty(token)) {
            return token;
        }

        // cookie中获取
        token = requestSpringMVC.getCookieValue(properties.getTokenName());
        if (!StringUtils.isEmpty(token)) {
            return token;
        }

        // 请求域中获取
        token = requestSpringMVC.getParam(properties.getTokenNameRequest());
        if (!StringUtils.isEmpty(token)) {
            return token;
        }

        throw new FinalTokenException(FinalConstants.NOT_EXIST_TOKEN);
    }

    private static ServletRequestAttributes getServletRequestAttributes() {
        return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    }

    public FinalExceptionHandler getExceptionHandler() {
        return exceptionHandler;
    }

    public FinalSecurityProperties getSecurityProperties() {
        return securityProperties;
    }

    public FinalTokenGenerate getTokenGenerate() {
        return tokenGenerate;
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }

    public FinalSessionListener getSessionListener() {
        return sessionListener;
    }

    public FinalHttpSecurity getHttpSecurity() {
        return httpSecurity;
    }

    public FinalSecurityProperties getProperties() {
        return properties;
    }

    public FinalSecurityConfiguration getConfiguration() {
        return configuration;
    }
}
