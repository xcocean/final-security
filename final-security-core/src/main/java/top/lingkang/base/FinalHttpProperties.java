package top.lingkang.base;

import top.lingkang.base.impl.DefaultFinalExceptionHandler;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author lingkang
 * Created by 2022/1/7
 */
public class FinalHttpProperties {
    // 排除路径
    private String[] excludePath = {};

    // 权限校验
    private CheckAuthorize checkAuthorize =new CheckAuthorize();

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

    public FinalHttpProperties setExcludePath(String... excludePath) {
        Set<String> ex = new HashSet<>(Arrays.asList(excludePath));
        this.excludePath = ex.toArray(new String[ex.size()]);
        return this;
    }

    public CheckAuthorize checkAuthorize(){
        return checkAuthorize;
    }

    public CheckAuthorize getCheckAuthorize() {
        return checkAuthorize;
    }
}
