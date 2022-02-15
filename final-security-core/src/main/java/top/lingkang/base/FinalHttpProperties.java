package top.lingkang.base;

import top.lingkang.base.impl.DefaultFinalExceptionHandler;

import java.util.HashMap;

/**
 * @author lingkang
 * Created by 2022/1/7
 */
public class FinalHttpProperties {
    // 排除路径
    private String[] excludePath = {};

    // 权限校验
    private HashMap<String, FinalAuth> checkAuths = new HashMap<>();

    private FinalExceptionHandler exceptionHandler = new DefaultFinalExceptionHandler();

    public FinalExceptionHandler getExceptionHandler() {
        return exceptionHandler;
    }

    public FinalHttpProperties setExceptionHandler(FinalExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
        return this;
    }

    public String[] getExcludePath() {
        return excludePath;
    }

    public FinalHttpProperties setExcludePath(String[] excludePath) {
        this.excludePath = excludePath;
        return this;
    }

    public HashMap<String, FinalAuth> getCheckAuths() {
        return checkAuths;
    }

    public FinalHttpProperties setCheckAuths(HashMap<String, FinalAuth> checkAuths) {
        this.checkAuths = checkAuths;
        return this;
    }
}
