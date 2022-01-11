package top.lingkang;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import top.lingkang.base.FinalExceptionHandler;
import top.lingkang.base.FinalHttpSecurity;
import top.lingkang.base.FinalSessionListener;
import top.lingkang.base.FinalTokenGenerate;
import top.lingkang.config.FinalProperties;
import top.lingkang.config.FinalSecurityConfiguration;
import top.lingkang.constants.FinalConstants;
import top.lingkang.error.FinalTokenException;
import top.lingkang.filter.FinalAccessFilter;
import top.lingkang.filter.FinalFilterChain;
import top.lingkang.filter.FinalPrepareFilter;
import top.lingkang.http.FinalContextHolder;
import top.lingkang.http.FinalRequestContext;
import top.lingkang.session.FinalSession;
import top.lingkang.session.SessionManager;
import top.lingkang.session.impl.DefaultFinalSession;
import top.lingkang.utils.AuthUtils;
import top.lingkang.utils.CookieUtils;
import top.lingkang.utils.SpringBeanUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author lingkang
 * Created by 2022/1/7
 */
@ComponentScan("top.lingkang")
@Configuration
@EnableConfigurationProperties(FinalProperties.class)
public class FinalManager implements ApplicationRunner {
    private static final Log log = LogFactory.getLog(FinalManager.class);

    @Resource
    private FinalProperties finalProperties;
    private FinalExceptionHandler exceptionHandler;
    private FinalTokenGenerate tokenGenerate;
    private SessionManager sessionManager;
    private FinalSessionListener sessionListener;
    private FinalHttpSecurity httpSecurity;
    private FinalProperties properties;
    private FinalFilterChain[] filterChains;
    private FinalSecurityConfiguration configuration;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if ((configuration = SpringBeanUtils.getBean(FinalSecurityConfiguration.class)) == null) {
            configuration = new FinalSecurityConfiguration();
        }
        exceptionHandler = configuration.getExceptionHandler();
        tokenGenerate = configuration.getTokenGenerate();
        sessionManager = configuration.getSessionManager();
        sessionListener = configuration.getSessionListener();
        httpSecurity = configuration.getHttpSecurity();
        properties = configuration.getProperties();
        if (properties != null) {
            log.info("\nNote that application The configuration in YML is overwritten by a custom bean, \n" +
                    "注意，application.yml 中的 final.security._ 配置被自定义Bean（FinalSecurityConfiguration）覆盖");
        } else {
            properties = finalProperties;
        }

        initExcludePath();
        initFilterChain();

        log.info("final-security init user:  " + login("user"));
        log.info("final-security v1.0.1 load finish");
    }

    private void initExcludePath() {
        if (httpSecurity.getExcludePath() != null) {
            Set<String> exc = new HashSet<>(Arrays.asList(properties.getExcludePath()));
            exc.addAll(new HashSet<>(httpSecurity.getExcludePath()));
            properties.setExcludePath(exc.toArray(new String[exc.size()]));
        }
    }

    private void initFilterChain() {
        if (properties.getPrepareCheck()) {
            filterChains = AuthUtils.addFilterChain(filterChains, new FinalPrepareFilter(this));
        }
        filterChains = AuthUtils.addFilterChain(filterChains, new FinalAccessFilter(this));
    }

    public String login(String id) {
        if (properties.getOnlyOne()) { // 用户只能存在一个会话
            FinalSession session = sessionManager.getSessionById(id);
            if (session != null) {
                sessionManager.removeSession(session.getToken());
            }
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
        // ServletRequestAttributes servletRequestAttributes = getServletRequestAttributes();
        sessionManager.addFinalSession(token, session);// 共享会话时，会出现会话覆盖

        // 将token放到当前线程的变量中
        FinalRequestContext requestContext = FinalContextHolder.getRequestContext();
        if (requestContext != null) {
            requestContext.setToken(token);

            // 将令牌放到cookie中
            if (properties.getUseCookie()) {
                CookieUtils.addToken(
                        requestContext.getResponse(),
                        properties.getTokenName(),
                        token,
                        properties.getMaxValid() / 1000
                );
            }
        } else {
            FinalContextHolder.setRequestContext(new FinalRequestContext(token));
        }

        // 会话创建监听
        sessionListener.create(token, id, requestContext == null ? null : requestContext.getRequest());

        return token;
    }

    /**
     * 获取token
     */
    public String getToken() {
        // ThreadLocal 中获取
        FinalRequestContext requestContext = FinalContextHolder.getRequestContext();
        if (requestContext == null) {
            throw new FinalTokenException(FinalConstants.NOT_EXIST_TOKEN);
        }

        String token = requestContext.getToken();
        if (token != null) {
            return token;
        }


        // 请求头中获取
        token = requestContext.getRequest().getHeader(properties.getTokenNameHeader());
        if (token != null) {
            requestContext.setToken(token);
            return token;
        }

        // cookie中获取
        token = CookieUtils.getTokenByCookie(properties.getTokenName(), requestContext.getRequest().getCookies());
        if (token != null) {
            requestContext.setToken(token);
            return token;
        }

        // 请求域中获取
        token = requestContext.getRequest().getParameter(properties.getTokenNameRequest());
        if (token != null) {
            requestContext.setToken(token);
            return token;
        }

        throw new FinalTokenException(FinalConstants.NOT_EXIST_TOKEN);
    }

    // 配置区 start ----------------------------------

    public FinalExceptionHandler getExceptionHandler() {
        return exceptionHandler;
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

    public FinalSecurityConfiguration getConfiguration() {
        return configuration;
    }

    public FinalFilterChain[] getFilterChains() {
        return filterChains;
    }

    public void setFilterChains(FinalFilterChain[] filterChains) {
        this.filterChains = filterChains;
    }

    public FinalProperties getProperties() {
        return properties;
    }

    // 配置区 end ----------------------------------
}
