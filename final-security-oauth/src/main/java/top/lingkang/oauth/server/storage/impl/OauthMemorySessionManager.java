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
    private ConcurrentMap<String, String> idRSession = new ConcurrentHashMap<>();
    private ConcurrentMap<String, FinalSession> rSession = new ConcurrentHashMap<>();
    private ConcurrentMap<String, String[]> rRole = new ConcurrentHashMap<>();
    private ConcurrentMap<String, String[]> rPermission = new ConcurrentHashMap<>();

    @Override
    public void addRefreshSession(String refreshToken, FinalSession finalSession) {
        rSession.put(refreshToken, finalSession);
        idRSession.put(finalSession.getId(), refreshToken);
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
        if (remove != null)
            idRSession.remove(remove.getId());
        return remove;
    }

    @Override
    public FinalSession getRefreshSessionById(String id) {
        String s = idRSession.get(id);
        if (s != null)
            return rSession.get(s);
        return null;
    }

    @Override
    public String[] getRefreshRole(String refreshToken) {
        return rRole.get(refreshToken);
    }

    @Override
    public String[] updateRefreshRole(String refreshToken, String... role) {
        if (role == null)
            return null;
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
    public String[] updateRefreshPermission(String refreshToken, String... permission) {
        if (permission == null)
            return null;
        return rPermission.put(refreshToken, permission);
    }

    @Override
    public String[] deleteRefreshPermission(String refreshToken) {
        return rPermission.remove(refreshToken);
    }
}
