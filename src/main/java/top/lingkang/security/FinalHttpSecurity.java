package top.lingkang.security;

import java.util.HashMap;

/**
 * @author lingkang
 * @date 2021/8/20 17:33
 * @description
 */
public class FinalHttpSecurity {
    private HashMap<String, CheckAuth> checkAuthHashMap;

    public HashMap<String, CheckAuth> getCheckAuthHashMap() {
        return checkAuthHashMap;
    }

    public void setCheckAuthHashMap(HashMap<String, CheckAuth> checkAuthHashMap) {
        this.checkAuthHashMap = checkAuthHashMap;
    }
}
