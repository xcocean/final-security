package top.lingkang.config;

import top.lingkang.error.FinalExceptionHandler;
import top.lingkang.security.FinalAuthConfig;
import top.lingkang.session.FinalTokenGenerate;
import top.lingkang.session.SessionListener;

import java.util.List;

/**
 * @author lingkang
 * @date 2021/8/16 11:09
 * @description 加载配置文件
 */
public class FinalSecurityConfig {
    // token生成策略
    private FinalTokenGenerate finalTokenGenerate;
    // 会话监听
    private SessionListener sessionListener;
    // 排除路径
    private List<String> excludePath;
    // 异常处理
    private FinalExceptionHandler finalExceptionHandler;
    // 鉴权配置
    private FinalAuthConfig finalAuthConfig;

    public FinalAuthConfig getFinalAuthConfig() {
        return finalAuthConfig;
    }

    public void setFinalAuthConfig(FinalAuthConfig finalAuthConfig) {
        this.finalAuthConfig = finalAuthConfig;
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

    public List<String> getExcludePath() {
        return excludePath;
    }

    public void setExcludePath(List<String> excludePath) {
        this.excludePath = excludePath;
    }
}
