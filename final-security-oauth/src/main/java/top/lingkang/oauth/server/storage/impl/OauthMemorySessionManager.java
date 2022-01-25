package top.lingkang.oauth.server.storage.impl;

import top.lingkang.oauth.server.storage.OauthStorageManager;
import top.lingkang.session.FinalSession;
import top.lingkang.session.impl.FinalMemorySessionManager;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author lingkang
 * @date 2021/8/20 10:14
 * @description 会话管理，存储于内存中
 */
public class OauthMemorySessionManager extends FinalMemorySessionManager implements OauthStorageManager {
    private ConcurrentMap<String, FinalSession> rSession = new ConcurrentHashMap<String, FinalSession>();
    private ConcurrentMap<String, String[]> rRole = new ConcurrentHashMap<>();
    private ConcurrentMap<String, String[]> rPermission = new ConcurrentHashMap<>();

    @Override
    public void addRefreshSession(String refreshToken, FinalSession finalSession) {
        rSession.put(refreshToken, finalSession);
    }

    @Override
    public FinalSession getRefreshSession(String refreshToken) {
        return rSession.get(refreshToken);
    }

    @Override
    public FinalSession removeRefreshSession(String refreshToken) {
        FinalSession remove = rSession.remove(refreshToken);
        rRole.remove(refreshToken);
        rPermission.remove(refreshToken);
        return remove;
    }

    @Override
    public String[] getRefreshRole(String refreshToken) {
        return rRole.get(refreshToken);
    }

    @Override
    public String[] updateRefreshRole(String refreshToken, String[] role) {
        return rRole.put(refreshToken, role);
    }

    @Override
    public String[] deleteRefreshRole(String refreshToken) {
        return rRole.remove(refreshToken);
    }

    @Override
    public String[] getRefreshPermission(String refreshToken) {
        return rPermission.get(refreshToken);
    }

    @Override
    public String[] updateRefreshPermission(String refreshToken, String[] permission) {
        return rPermission.put(refreshToken, permission);
    }

    @Override
    public String[] deleteRefreshPermission(String refreshToken) {
        return rPermission.remove(refreshToken);
    }
}
