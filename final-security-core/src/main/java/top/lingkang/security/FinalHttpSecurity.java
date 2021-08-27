package top.lingkang.security;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author lingkang
 * @date 2021/8/20 17:33
 * @description
 */
public class FinalHttpSecurity {
    // 排除路径
    private List<String> excludePath;
    // 权限校验
    private HashMap<String, CheckAuth> checkAuthHashMap;

    public HashMap<String, CheckAuth> getCheckAuthHashMap() {
        return checkAuthHashMap;
    }

    public void setCheckAuthHashMap(HashMap<String, CheckAuth> checkAuthHashMap) {
        this.checkAuthHashMap = checkAuthHashMap;
    }

    public void setExcludePath(List<String> excludePath) {
        this.excludePath = excludePath;
    }

    public void setExcludePath(String... excludePath) {
        this.excludePath = Arrays.asList(excludePath);
    }

    public List<String> getExcludePath() {
        return excludePath;
    }
}
