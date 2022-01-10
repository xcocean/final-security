package top.lingkang.config;

import top.lingkang.base.FinalExceptionHandler;
import top.lingkang.base.FinalHttpSecurity;
import top.lingkang.base.FinalSessionListener;
import top.lingkang.base.FinalTokenGenerate;
import top.lingkang.base.impl.DefaultFinalExceptionHandler;
import top.lingkang.base.impl.DefaultFinalHttpSecurity;
import top.lingkang.base.impl.DefaultFinalSessionListener;
import top.lingkang.base.impl.DefaultFinalTokenGenerate;
import top.lingkang.session.SessionManager;
import top.lingkang.session.impl.DefaultFinalSessionManager;

/**
 * @author lingkang
 * @date 2022/1/8
 */
public class FinalSecurityConfiguration extends FinalProperties{
    private FinalExceptionHandler exceptionHandler=new DefaultFinalExceptionHandler();
    private FinalTokenGenerate tokenGenerate=new DefaultFinalTokenGenerate();
    private SessionManager sessionManager=new DefaultFinalSessionManager();
    private FinalSessionListener sessionListener=new DefaultFinalSessionListener();
    private  FinalHttpSecurity httpSecurity=new DefaultFinalHttpSecurity();

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

    public FinalHttpSecurity getHttpSecurity() {
        return httpSecurity;
    }

    public void setHttpSecurity(FinalHttpSecurity httpSecurity) {
        this.httpSecurity = httpSecurity;
    }
}
