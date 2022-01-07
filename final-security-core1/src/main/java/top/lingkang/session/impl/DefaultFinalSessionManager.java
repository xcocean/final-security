package top.lingkang.session.impl;

import top.lingkang.constants.FinalConstants;
import top.lingkang.error.FinalTokenException;
import top.lingkang.session.FinalSession;
import top.lingkang.session.SessionManager;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author lingkang
 * @date 2021/8/20 10:14
 * @description
 */
public class DefaultFinalSessionManager implements SessionManager {
    private static ConcurrentMap<String, FinalSession> session = new ConcurrentHashMap<String, FinalSession>();
    private static ConcurrentMap<String, List<String>> roles = new ConcurrentHashMap<String, List<String>>();
    private static ConcurrentMap<String, List<String>> permission = new ConcurrentHashMap<String, List<String>>();


    @Override
    public void addFinalSession(String token, FinalSession finalSession) {
        session.put(token, finalSession);
    }

    @Override
    public FinalSession getSession(String token) {
        return session.get(token);
    }

    @Override
    public FinalSession getSessionById(String id) {
        for (Map.Entry<String, FinalSession> map : session.entrySet()) {
            if (map.getValue().getId().equals(id)) {
                return map.getValue();
            }
        }
        return null;
    }

    @Override
    public List<String> getRoles(String token) {
        return roles.get(token);
    }

    @Override
    public void addRoles(String token, List<String> roles) {
        this.roles.put(token, roles);
    }

    @Override
    public List<String> getPermission(String token) {
        return permission.get(token);
    }

    @Override
    public void updateRoles(String token, List<String> roles) {
        this.roles.put(token, roles);
    }

    @Override
    public void deleteRoles(String token) {
        roles.remove(token);
    }

    @Override
    public void addPermission(String token, List<String> permission) {
        this.permission.put(token, permission);
    }

    @Override
    public void updatePermission(String token, List<String> permission) {
        this.permission.put(token, permission);
    }

    @Override
    public void deletePermission(String token) {
        permission.remove(token);
    }

    @Override
    public void removeSession(String token) {
        session.remove(token);
        roles.remove(token);
        permission.remove(token);
    }

    public boolean existsToken(String token) {
        return session.containsKey(token);
    }

    @Override
    public void updateLastAccessTime(String token) {
        FinalSession finalSession = session.get(token);
        if (finalSession == null) {
            throw new FinalTokenException(FinalConstants.NOT_EXIST_TOKEN);
        }
        finalSession.updateLastAccessTime();
        session.put(token, finalSession);
    }

    @Override
    public long getExpire(String token) {
        return 0L;
    }
}
