package top.lingkang;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import top.lingkang.base.FinalExceptionHandler;
import top.lingkang.base.FinalHttpSecurity;
import top.lingkang.base.FinalSessionListener;
import top.lingkang.base.FinalTokenGenerate;
import top.lingkang.config.FinalConfigProperties;
import top.lingkang.config.FinalProperties;
import top.lingkang.constants.FinalConstants;
import top.lingkang.error.FinalTokenException;
import top.lingkang.filter.CheckTokenValidFilter;
import top.lingkang.filter.FinalAuthenticationFilter;
import top.lingkang.filter.FinalFilterChain;
import top.lingkang.filter.UpdateLastAccessTimeFilter;
import top.lingkang.holder.FinalHolder;
import top.lingkang.http.FinalContextHolder;
import top.lingkang.http.FinalRequestContext;
import top.lingkang.session.SessionManager;
import top.lingkang.utils.AuthUtils;
import top.lingkang.utils.BeanUtils;
import top.lingkang.utils.CookieUtils;

import javax.annotation.PreDestroy;
import java.util.*;

/**
 * @author lingkang
 * Created by 2022/1/7
 */
public class FinalManager implements InitializingBean {
    private static final Log log = LogFactory.getLog(FinalManager.class);
    @Autowired
    private FinalExceptionHandler exceptionHandler;
    @Autowired
    private FinalTokenGenerate tokenGenerate;
    @Autowired
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

    private Timer cleanExpiresTimer;

    @Override
    public void afterPropertiesSet() throws Exception {
        BeanUtils.copyProperty(configProperties, properties, true);

        initExcludePath();
        initFilterChain();
        initCleanExpires();

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
        List<FinalFilterChain> chains = new ArrayList<>();
        chains.add(new CheckTokenValidFilter(this));
        if (getProperties().getTokenAccessContinue())
            chains.add(new UpdateLastAccessTimeFilter(this));
        chains.add(new FinalAuthenticationFilter(this));

        // 添加过滤链
        filterChains = AuthUtils.addFilterChain(
                filterChains,
                chains.toArray(new FinalFilterChain[chains.size()]));
    }

    private void initCleanExpires() {
        if ("FinalMemorySessionManager".equals(sessionManager.getClass().getSimpleName())) {
            cleanExpiresTimer = new Timer();
            cleanExpiresTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    sessionManager.cleanExpires();
                }
            }, 5000, 21600000);// 21600000ms = 6小时 执行一次
        }
    }

    @PreDestroy
    public void destroy() {
        if (cleanExpiresTimer != null)
            cleanExpiresTimer.cancel();
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

        // 请求头中获取
        token = requestContext.getRequest().getHeader(properties.getTokenNameHeader());
        if (token != null) {
            requestContext.setToken(token.substring(properties.getTokenNameHeaderPrefix().length()));
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

    // 配置区 end ----------------------------------

    public void updateFilterChains(FinalFilterChain[] newFilterChain) {
        filterChains = newFilterChain;
    }

    public void updateExcludePath(String... excludePath) {
        properties.setExcludePath(excludePath);
    }

    public void updateTokenMaxTime(long tokenMax) {
        this.properties.setMaxValid(tokenMax);
    }
}
