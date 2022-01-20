package top.lingkang.oauth.server.storage.impl;

import org.springframework.beans.factory.annotation.Autowired;
import top.lingkang.oauth.server.OauthServerManager;
import top.lingkang.oauth.server.entity.OauthToken;
import top.lingkang.oauth.server.storage.OauthStorageManager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author lingkang
 * Created by 2022/1/17
 */
public class DefaultOauthStorageManager implements OauthStorageManager {
    private static final ConcurrentMap<String, Long> codeManager = new ConcurrentHashMap<>();
    private static final ConcurrentMap<String, OauthToken> sToken = new ConcurrentHashMap<>();
    private static final ConcurrentMap<String, OauthToken> sRefresh = new ConcurrentHashMap<>();
    private static final ConcurrentMap<String, String> idAndToken = new ConcurrentHashMap<>();
    private static ConcurrentMap<String, String[]> roles = new ConcurrentHashMap<String, String[]>();
    private static ConcurrentMap<String, String[]> permission = new ConcurrentHashMap<String, String[]>();

    @Autowired
    private OauthServerManager serverManager;

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

    @Override
    public void addToken(String token, OauthToken oauthToken) {
        sToken.put(token, oauthToken);
        idAndToken.put(oauthToken.getId(), token);
    }

    @Override
    public void addRefreshToken(String refreshToken, OauthToken token) {
        sRefresh.put(refreshToken, token);
    }

    @Override
    public String[] getRoles(String token) {
        return roles.get(token);
    }

    @Override
    public String[] getPermission(String token) {
        return permission.get(token);
    }

    @Override
    public void addRoles(String token, String... role) {
        roles.put(token, role);
    }

    @Override
    public String[] deleteRoles(String token) {
        return roles.remove(token);
    }

    @Override
    public void addPermission(String token, String... permission) {
        this.permission.put(token, permission);
    }

    @Override
    public String[] deletePermission(String token) {
        return permission.remove(token);
    }

    @Override
    public String getTokenById(String id) {
        return idAndToken.get(id);
    }

    @Override
    public OauthToken getToken(String token) {
        return sToken.get(token);
    }

    @Override
    public OauthToken getRefreshToken(String refreshToken) {
        return sRefresh.get(refreshToken);
    }

    @Override
    public OauthToken removeToken(String token) {
        OauthToken oauthToken = sRefresh.remove(token);
        if (oauthToken != null)
            idAndToken.remove(oauthToken.getId());
        roles.remove(token);
        permission.remove(token);
        return oauthToken;
    }

    @Override
    public OauthToken removeRefreshToken(String refreshToken) {
        return sRefresh.remove(refreshToken);
    }

    @Override
    public void cleanExpires() {
        long current = System.currentTimeMillis();
        if (!codeManager.isEmpty()) {
            long codeMaxValid = serverManager.getOauthServerProperties().getCodeMaxValid();
            for (Map.Entry<String, Long> entry : codeManager.entrySet()) {
                if (current - entry.getValue() > codeMaxValid) {
                    removeCode(entry.getKey());
                }
            }
        }

        if (!sToken.isEmpty()) {
            long tokenMaxTime = serverManager.getOauthServerProperties().getTokenMaxTime();
            for (Map.Entry<String, OauthToken> entry : sToken.entrySet()) {
                if (current - entry.getValue().getCreateTime() > tokenMaxTime) {
                    removeToken(entry.getKey());
                }
            }
        }

        if (!sRefresh.isEmpty()) {
            long maxTime = serverManager.getOauthServerProperties().getRefreshTokenMaxTime();
            for (Map.Entry<String, OauthToken> entry : sRefresh.entrySet()) {
                if (current - entry.getValue().getCreateTime() > maxTime) {
                    removeRefreshToken(entry.getKey());
                }
            }
        }
    }
}
