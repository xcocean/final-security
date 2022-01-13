package top.lingkang.session.impl;

import top.lingkang.constants.FinalConstants;
import top.lingkang.error.FinalTokenException;
import top.lingkang.session.FinalSession;
import top.lingkang.session.SessionManager;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author lingkang
 * @date 2021/8/20 10:14
 * @description 会话管理，存储于内存中
 */
public class DefaultFinalSessionManager implements SessionManager {
    private static ConcurrentMap<String, String> idAndToken = new ConcurrentHashMap<>();
    private static ConcurrentMap<String, FinalSession> session = new ConcurrentHashMap<String, FinalSession>();
    private static ConcurrentMap<String, String[]> roles = new ConcurrentHashMap<String, String[]>();
    private static ConcurrentMap<String, String[]> permission = new ConcurrentHashMap<String, String[]>();

    @Override
    public void addFinalSession(String token, FinalSession finalSession) {
        session.put(token, finalSession);
        idAndToken.put(finalSession.getId(), token);
    }

    @Override
    public FinalSession getSession(String token) {
        FinalSession session = this.session.get(token);
        if (session == null)
            throw new FinalTokenException(FinalConstants.NOT_EXIST_TOKEN);
        return session;
    }

    @Override
    public FinalSession getSessionById(String id) {
        FinalSession session = DefaultFinalSessionManager.session.get(idAndToken.get(id));
        if (session == null)
            throw new FinalTokenException(FinalConstants.NOT_EXIST_TOKEN);
        return session;
    }

    @Override
    public String[] getRoles(String token) {
        return roles.get(token);
    }

    @Override
    public void addRoles(String token, String... roles) {
        this.roles.put(token, roles);
    }

    @Override
    public String[] getPermission(String token) {
        return permission.get(token);
    }

    @Override
    public void updateRoles(String token, String... roles) {
        this.roles.put(token, roles);
    }

    @Override
    public void deleteRoles(String token) {
        roles.remove(token);
    }

    @Override
    public void addPermission(String token, String... permission) {
        this.permission.put(token, permission);
    }

    @Override
    public void updatePermission(String token, String... permission) {
        this.permission.put(token, permission);
    }

    @Override
    public void deletePermission(String token) {
        permission.remove(token);
    }

    @Override
    public FinalSession removeSession(String token) {
        FinalSession remove = session.remove(token);
        if (remove != null) {
            idAndToken.remove(remove.getId());
        }
        roles.remove(token);
        permission.remove(token);
        return remove;
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
    }

    @Override
    public long getLastAccessTime(String token) {
        FinalSession session = this.session.get(token);
        if (session == null) {
            throw new FinalTokenException(FinalConstants.NOT_EXIST_TOKEN);
        }
        return session.getLastAccessTime();
    }
}
