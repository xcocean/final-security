package top.lingkang.config;

import top.lingkang.error.FinalExceptionHandler;
import top.lingkang.security.FinalHttpSecurity;
import top.lingkang.session.FinalTokenGenerate;
import top.lingkang.session.SessionListener;
import top.lingkang.session.SessionManager;

/**
 * @author lingkang
 * @date 2021/8/16 11:09
 * @description 加载配置文件
 */
public class FinalSecurityConfig {
    // token生成策略
    private FinalTokenGenerate finalTokenGenerate;
    private SessionManager sessionManager;
    // 会话监听
    private SessionListener sessionListener;
    // 异常处理
    private FinalExceptionHandler finalExceptionHandler;

    private long sessionMaxValid;

    private FinalHttpSecurity finalHttpSecurity;

    public FinalHttpSecurity getFinalHttpSecurity() {
        return finalHttpSecurity;
    }

    public void setFinalHttpSecurity(FinalHttpSecurity finalHttpSecurity) {
        this.finalHttpSecurity = finalHttpSecurity;
    }

    public long getSessionMaxValid() {
        return sessionMaxValid;
    }

    public void setSessionMaxValid(long sessionMaxValid) {
        this.sessionMaxValid = sessionMaxValid;
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public FinalExceptionHandler getFinalExceptionHandler() {
        return finalExceptionHandler;
    }

    public void setFinalExceptionHandler(FinalExceptionHandler finalExceptionHandler) {
        this.finalExceptionHandler = finalExceptionHandler;
    }

    public FinalTokenGenerate getFinalTokenGenerate() {
        return finalTokenGenerate;
    }

    public void setFinalTokenGenerate(FinalTokenGenerate finalTokenGenerate) {
        this.finalTokenGenerate = finalTokenGenerate;
    }

    public SessionListener getSessionListener() {
        return sessionListener;
    }

    public void setSessionListener(SessionListener sessionListener) {
        this.sessionListener = sessionListener;
    }

}
