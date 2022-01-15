package top.lingkang;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import top.lingkang.base.FinalExceptionHandler;
import top.lingkang.base.FinalHttpSecurity;
import top.lingkang.base.FinalSessionListener;
import top.lingkang.base.FinalTokenGenerate;
import top.lingkang.base.impl.DefaultFinalExceptionHandler;
import top.lingkang.base.impl.DefaultFinalHttpSecurity;
import top.lingkang.base.impl.DefaultFinalSessionListener;
import top.lingkang.base.impl.DefaultFinalTokenGenerate;
import top.lingkang.config.FinalConfigProperties;
import top.lingkang.config.FinalProperties;
import top.lingkang.constants.FinalConstants;
import top.lingkang.error.FinalTokenException;
import top.lingkang.filter.FinalAuthenticationFilter;
import top.lingkang.filter.FinalBaseFilter;
import top.lingkang.filter.FinalFilterChain;
import top.lingkang.holder.FinalHolder;
import top.lingkang.http.FinalContextHolder;
import top.lingkang.http.FinalRequestContext;
import top.lingkang.session.SessionManager;
import top.lingkang.session.impl.DefaultFinalSessionManager;
import top.lingkang.utils.AuthUtils;
import top.lingkang.utils.BeanUtils;
import top.lingkang.utils.CookieUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author lingkang
 * Created by 2022/1/7
 */
@ComponentScan("top.lingkang")
@Configuration
public class FinalManager implements ApplicationRunner {
    private static final Log log = LogFactory.getLog(FinalManager.class);
    @Autowired(required = false)
    private FinalExceptionHandler exceptionHandler;
    @Autowired(required = false)
    private FinalTokenGenerate tokenGenerate;
    @Autowired(required = false)
    private SessionManager sessionManager;
    @Autowired(required = false)
    private FinalSessionListener sessionListener;
    @Autowired(required = false)
    private FinalHttpSecurity httpSecurity;
    @Autowired
    private FinalProperties properties;
    private FinalFilterChain[] filterChains;
    @Autowired(required = false)
    private FinalConfigProperties configProperties;
    @Autowired(required = false)
    private FinalHolder finalHolder;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (exceptionHandler == null) exceptionHandler = new DefaultFinalExceptionHandler();
        if (tokenGenerate == null) tokenGenerate = new DefaultFinalTokenGenerate();
        if (sessionManager == null) sessionManager = new DefaultFinalSessionManager();
        if (sessionListener == null) sessionListener = new DefaultFinalSessionListener();
        if (httpSecurity == null) httpSecurity = new DefaultFinalHttpSecurity();
        if (configProperties == null) configProperties = new FinalConfigProperties();

        BeanUtils.copyProperty(configProperties, properties, true);

        initExcludePath();
        initFilterChain();

        log.info("final-security init user:  " + finalHolder.login("user", null, null, null));
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
        filterChains = AuthUtils.addFilterChain(
                filterChains,
                new FinalBaseFilter(this),
                new FinalAuthenticationFilter(this));
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

    public FinalFilterChain[] getFilterChains() {
        return filterChains;
    }

    public FinalProperties getProperties() {
        return properties;
    }

    public void updateProperties(FinalProperties newProperties) {
        properties = newProperties;
    }

    // 配置区 end ----------------------------------

    public void updateFilterChains(FinalFilterChain[] newFilterChain) {
        filterChains = newFilterChain;
    }
}
