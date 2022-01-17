package top.lingkang.oauth.server.storage.impl;

import top.lingkang.oauth.server.storage.OauthStorageManager;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author lingkang
 * Created by 2022/1/17
 */
public class DefaultOauthStorageManager implements OauthStorageManager {
    private static final ConcurrentMap<String, Long> codeManager = new ConcurrentHashMap<>();

    @Override
    public boolean addCode(String code) {
        if (codeManager.containsKey(code)) {
            return false;
        }
        codeManager.put(code, System.currentTimeMillis());
        return true;
    }

    @Override
    public boolean removeCode(String code) {
        return codeManager.remove(code) != null;
    }
}
