package top.lingkang.config;

import org.springframework.stereotype.Component;
import top.lingkang.base.FinalExceptionHandler;
import top.lingkang.base.FinalSessionListener;
import top.lingkang.base.FinalTokenGenerate;
import top.lingkang.base.impl.DefaultFinalExceptionHandler;
import top.lingkang.base.impl.DefaultFinalSessionListener;
import top.lingkang.base.impl.DefaultFinalTokenGenerate;
import top.lingkang.session.SessionManager;
import top.lingkang.session.impl.DefaultFinalSessionManager;
import top.lingkang.utils.SpringBeanUtils;

/**
 * @author lingkang
 * Created by 2022/1/7
 */
@Component
public class FinalSecurityConfig {
    private FinalExceptionHandler exceptionHandler;
    private FinalTokenGenerate tokenGenerate;
    private SessionManager sessionManager;
    private FinalSessionListener sessionListener;

    public FinalSecurityConfig() {
        if (!SpringBeanUtils.isExistsBean(FinalExceptionHandler.class)) {
            setExceptionHandler(new DefaultFinalExceptionHandler());
        }
        if (!SpringBeanUtils.isExistsBean(FinalTokenGenerate.class)) {
            setTokenGenerate(new DefaultFinalTokenGenerate());
        }
        if (!SpringBeanUtils.isExistsBean(SessionManager.class)) {
            setSessionManager(new DefaultFinalSessionManager());
        }
        if (!SpringBeanUtils.isExistsBean(FinalSessionListener.class)) {
            setSessionListener(new DefaultFinalSessionListener());
        }
    }

    public FinalExceptionHandler getExceptionHandler() {
        return exceptionHandler;
    }

    public void setExceptionHandler(FinalExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }

    public FinalTokenGenerate getTokenGenerate() {
        return tokenGenerate;
    }

    public void setTokenGenerate(FinalTokenGenerate tokenGenerate) {
        this.tokenGenerate = tokenGenerate;
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public FinalSessionListener getSessionListener() {
        return sessionListener;
    }

    public void setSessionListener(FinalSessionListener sessionListener) {
        this.sessionListener = sessionListener;
    }
}
