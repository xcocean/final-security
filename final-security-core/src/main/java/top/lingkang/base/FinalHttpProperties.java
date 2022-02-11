package top.lingkang.base;

import top.lingkang.base.FinalAuth;
import top.lingkang.base.impl.DefaultFinalExceptionHandler;

import java.util.HashMap;

/**
 * @author lingkang
 * Created by 2022/1/7
 */
public class FinalHttpProperties {
    // 排除路径
    private String[] excludePath={};

    // 权限校验
    private HashMap<String, FinalAuth> checkAuths = new HashMap<>();

    private FinalExceptionHandler exceptionHandler=new DefaultFinalExceptionHandler();;

    public FinalExceptionHandler getExceptionHandler() {
        return exceptionHandler;
    }

    public void setExceptionHandler(FinalExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }

    public String[] getExcludePath() {
        return excludePath;
    }

    public void setExcludePath(String[] excludePath) {
        this.excludePath = excludePath;
    }

    public HashMap<String, FinalAuth> getCheckAuths() {
        return checkAuths;
    }

    public void setCheckAuths(HashMap<String, FinalAuth> checkAuths) {
        this.checkAuths = checkAuths;
    }
}
