package top.lingkang;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
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
    @Autowired
    private FinalExceptionHandler exceptionHandler;
    @Autowired
    private FinalTokenGenerate tokenGenerate;
    @Autowired(required = false)
    private SessionManager sessionManager;
    @Autowired
    private FinalSessionListener sessionListener;
    @Autowired
    private FinalHttpSecurity httpSecurity;
    @Autowired
    private FinalProperties properties;
    private FinalFilterChain[] filterChains;
    @Autowired
    private FinalConfigProperties configProperties;
    @Autowired
    private FinalHolder finalHolder;

    @ConditionalOnMissingBean(FinalExceptionHandler.class)
    @Bean
    public FinalExceptionHandler exceptionHandler() {
        return new DefaultFinalExceptionHandler();
    }

    @ConditionalOnMissingBean(FinalTokenGenerate.class)
    @Bean
    public FinalTokenGenerate tokenGenerate() {
        return new DefaultFinalTokenGenerate();
    }

    @ConditionalOnMissingBean(FinalSessionListener.class)
    @Bean
    public FinalSessionListener finalSessionListener() {
        return new DefaultFinalSessionListener();
    }

    @ConditionalOnMissingBean(FinalHttpSecurity.class)
    @Bean
    public FinalHttpSecurity finalHttpSecurity() {
        return new DefaultFinalHttpSecurity();
    }

    @ConditionalOnMissingBean(FinalConfigProperties.class)
    @Bean
    public FinalConfigProperties finalConfigProperties() {
        return new FinalConfigProperties();
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (sessionManager == null)
            sessionManager = new DefaultFinalSessionManager();
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
            requestContext.setToken(token.substring(properties.getTokenNameHeaderPrefix().length()));
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
