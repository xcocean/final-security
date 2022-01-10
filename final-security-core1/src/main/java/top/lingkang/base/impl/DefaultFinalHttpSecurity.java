package top.lingkang.base.impl;

import top.lingkang.base.FinalAuth;
import top.lingkang.base.FinalHttpSecurity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author lingkang
 * @date 2022/1/8
 */
public class DefaultFinalHttpSecurity implements FinalHttpSecurity {
    // 排除路径
    private List<String> excludePath = new ArrayList<>();
    // 权限校验
    private HashMap<String, FinalAuth> checkAuths = new HashMap<>();

    public List<String> getExcludePath() {
        return excludePath;
    }

    public void setExcludePath(List<String> excludePath) {
        this.excludePath = excludePath;
    }

    public HashMap<String, FinalAuth> getCheckAuths() {
        return checkAuths;
    }

    public void setCheckAuths(HashMap<String, FinalAuth> checkAuths) {
        this.checkAuths = checkAuths;
    }
}
